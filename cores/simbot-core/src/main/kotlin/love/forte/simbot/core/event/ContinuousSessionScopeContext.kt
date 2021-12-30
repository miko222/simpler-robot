/*
 *  Copyright (c) 2021-2021 ForteScarlet <https://github.com/ForteScarlet>
 *
 *  根据 Apache License 2.0 获得许可；
 *  除非遵守许可，否则您不得使用此文件。
 *  您可以在以下网址获取许可证副本：
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   有关许可证下的权限和限制的具体语言，请参见许可证。
 */

package love.forte.simbot.core.event

import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import love.forte.simbot.ID
import love.forte.simbot.LoggerFactory
import love.forte.simbot.concurrentIDMapOf
import love.forte.simbot.event.*
import love.forte.simbot.toCharSequenceID
import java.util.concurrent.Future
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


internal class CoreContinuousSessionContext(
    override val coroutineScope: CoroutineScope,
    private val manager: ResumedListenerManager
) : ContinuousSessionContext() {

    private fun <T> waitingAsReceiver(
        continuation: CancellableContinuation<T>,
        id: ID,
        timeout: Long,
        listener: ResumedListener<T>
    ) {
        val deferred = CompletableDeferred<T>()
        val receiver = deferred.asSession(continuation)
        continuation.invokeOnCancellation(receiver::cancel) // invokeOnCancellation must only here
        val provider = continuation.asSession(deferred)

        manager.set(id, listener, provider, receiver)

        if (timeout > 0) {
            val timeoutJob = coroutineScope.launch(start = CoroutineStart.LAZY) {
                try {
                    withTimeout(timeout) {
                        delay(timeout + 1000)
                    }
                } catch (timeoutException: TimeoutCancellationException) {
                    // timeout
                    provider.cancel(timeoutException)
                }
            }
            timeoutJob.start().also {
                provider.invokeOnCompletion { timeoutJob.cancel() }
            }
        }
    }

    override suspend fun <T> waitingFor(id: ID, timeout: Long, listener: ResumedListener<T>): T =
        suspendCancellableCoroutine { c ->
            waitingAsReceiver(c, id, timeout, listener)
        }

    override suspend fun <E : Event, T> waitingFor(
        id: ID,
        timeout: Long,
        eventKey: Event.Key<E>,
        listener: ClearTargetResumedListener<E, T>
    ): T = waitingFor(id, timeout) { c, p ->
        if (eventKey isSubFrom c.event.key) {
            eventKey.safeCast(c.event)?.also { event -> listener(event, c, p) }
        }
    }


    override fun <T> waiting(id: ID, timeout: Long, listener: ResumedListener<T>): ContinuousSessionReceiver<T> {
        return coroutineScope.async {
            waitingFor(id, timeout, listener)
        }.asSession()
    }


    override fun <E : Event, T> waiting(
        id: ID,
        timeout: Long,
        eventKey: Event.Key<E>,
        listener: ClearTargetResumedListener<E, T>
    ): ContinuousSessionReceiver<T> = waiting(id, timeout) { c, p ->
        if (eventKey isSubFrom c.event.key) {
            eventKey.safeCast(c.event)?.also { event ->
                listener(event, c, p)
            }
        }
    }

    override fun <T> getProvider(id: ID): ContinuousSessionProvider<T>? {
        return manager.getProvider(id)
    }

    override fun <T> getReceiver(id: ID): ContinuousSessionReceiver<T>? {
        return manager.getReceiver(id)
    }
}

internal fun <T> Deferred<T>.asSession(continuation: CancellableContinuation<T>? = null): CoreContinuousSessionReceiver<T> =
    CoreContinuousSessionReceiver(continuation, this)


internal class CoreContinuousSessionReceiver<T>(
    private val continuation: CancellableContinuation<T>?,
    private val deferred: Deferred<T>
) : ContinuousSessionReceiver<T> {
    override suspend fun await(): T = deferred.await()

    override fun cancel(reason: Throwable?) {
        continuation?.cancel(reason)
        deferred.cancel(reason?.let { CancellationException(it.localizedMessage, it) })
    }

    override fun tryCancel(reason: Throwable?): Boolean {
        if (continuation != null && (continuation.isCompleted || continuation.isCancelled)) return false
        if (deferred.isCompleted || deferred.isCancelled) return false
        cancel(reason)
        return true
    }

    override fun asFuture(): Future<T> = deferred.asCompletableFuture()
}


internal fun <T> CancellableContinuation<T>.asSession(deferred: CompletableDeferred<T>): CoreContinuousSessionProvider<T> =
    CoreContinuousSessionProvider(this, deferred)

internal class CoreContinuousSessionProvider<T>(
    private val continuation: CancellableContinuation<T>,
    private val deferred: CompletableDeferred<T>
) : ContinuousSessionProvider<T> {
    override fun push(value: T) {
        continuation.resume(value)
        deferred.complete(value)
    }

    override fun pushException(e: Throwable) {
        continuation.resumeWithException(e)
        deferred.completeExceptionally(e)
    }

    override fun cancel(reason: Throwable?) {
        continuation.cancel(reason)
        deferred.cancel(reason?.let { CancellationException(reason.localizedMessage, reason) })
    }

    override fun invokeOnCompletion(handler: CompletionHandler) {
        deferred.invokeOnCompletion(handler)
    }

    override val isCompleted: Boolean
        get() = continuation.isCompleted
}


internal data class WaitingListener<T>(
    val listener: ResumedListener<T>,
    val provider: CoreContinuousSessionProvider<T>,
    val receiver: CoreContinuousSessionReceiver<T>
) {
    suspend operator fun invoke(context: EventProcessingContext) {
        listener(context, provider)
    }
}

internal class ResumedListenerManager {
    companion object {
        private val logger = LoggerFactory.getLogger(ResumedListenerManager::class)
    }

    private val listeners = concurrentIDMapOf<WaitingListener<*>>()

    /**
     * 会 cancel 被顶替的旧值。
     */
    fun <T> set(
        id: ID,
        listener: ResumedListener<T>,
        provider: CoreContinuousSessionProvider<T>,
        receiver: CoreContinuousSessionReceiver<T>
    ) {
        val cid = id.toCharSequenceID()
        val current = WaitingListener(listener, provider, receiver)
        listeners.merge(cid, current) { old, now ->
            old.provider.cancel(CancellationException("Replaced by the same ID listener"))
            now
        }
        provider.invokeOnCompletion {
            listeners.remove(cid)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getProvider(id: ID): ContinuousSessionProvider<T>? {
        return listeners[id]?.provider as? ContinuousSessionProvider<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getReceiver(id: ID): ContinuousSessionReceiver<T>? {
        return listeners[id]?.receiver as? ContinuousSessionReceiver<T>
    }

    fun isEmpty(): Boolean = listeners.isEmpty()

    suspend fun process(context: CoreEventProcessingContext, scope: CoroutineScope) {
        listeners.forEach { id, listener ->
            scope.launch {
                try {
                    listener(context)
                } catch (e: Throwable) {
                    logger.error("ResumedListener(id=$id) invoke failed: ${e.localizedMessage}", e)
                }
            }
        }
    }
}

