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

package love.forte.simbot.event

import love.forte.simbot.Api4J
import love.forte.simbot.ID
import love.forte.simbot.Interceptor
import love.forte.simbot.PriorityConstant

/**
 * 与事件有关的拦截器。拦截器是一个包裹在目标前后的一道 “关卡”,  通过拦截器可以自由定义对目标逻辑前后以及异常的处理。
 *
 * 比较常见与相似的概念，比如动态代理. 动态代理在逻辑上基本与拦截器一致。
 *
 *
 *
 * @see EventProcessingInterceptor
 * @see EventListenerInterceptor
 */
public sealed interface EventInterceptor<C : EventInterceptor.Context<R>, R> : Interceptor<C, R> {
    /**
     * 拦截器应该存在一个唯一ID。
     * 对于过程拦截器和监听函数拦截器，它们可以分别独立。
     */
    public val id: ID

    /**
     * 优先级。
     */
    public val priority: Int get() = PriorityConstant.NORMAL

    /**
     * 事件拦截器所拦截的目标内容。
     */
    public sealed interface Context<R> : Interceptor.Context<R> {

        /**
         * 事件处理流程的context。
         */
        public val eventContext: EventProcessingContext
    }
}


/**
 * 事件处理过程的拦截器。[EventProcessingInterceptor] 是针对 **整个** 事件流程的整体拦截，他不关系处理流程中的某个单独的监听函数，
 * 而是对所有被触发的事件流程的整体进行一次拦截。
 *
 * @see BlockingEventProcessingInterceptor
 */
public interface EventProcessingInterceptor :
    EventInterceptor<EventProcessingInterceptor.Context, EventProcessingResult> {
    @JvmSynthetic
    override suspend fun intercept(context: Context): EventProcessingResult

    /**
     * [EventProcessingInterceptor] 的传递上下文。
     */
    public interface Context : EventInterceptor.Context<EventProcessingResult> {
        override val eventContext: EventProcessingContext
    }
}

/**
 * 为Java提供的阻塞调用的 [EventProcessingInterceptor] 接口方案。
 *
 * @see EventProcessingInterceptor
 */
@Api4J
public interface BlockingEventProcessingInterceptor : EventProcessingInterceptor {
    public fun doIntercept(context: EventProcessingInterceptor.Context): EventProcessingResult

    @JvmSynthetic
    override suspend fun intercept(context: EventProcessingInterceptor.Context): EventProcessingResult =
        doIntercept(context)
}


/**
 * 事件监听函数拦截器。与 [EventProcessingInterceptor] 不同，[EventListenerInterceptor] 则针对一次事件处理流程中的 **每一个** [监听函数][EventListener] 进行独立拦截。
 *
 * 事件监听器不建议对 [EventListenerProcessingContext.textContent] 进行操作，尤其是在(boot下)同时使用了 [love.forte.simboot.listener.EventListenerTextContentProcessor] 的情况下。
 *
 * @see BlockingEventListenerInterceptor
 * @see love.forte.simboot.listener.EventListenerTextContentProcessor
 */
public interface EventListenerInterceptor : EventInterceptor<EventListenerInterceptor.Context, EventResult> {
    @JvmSynthetic
    override suspend fun intercept(context: Context): EventResult

    /**
     * [EventListenerInterceptor] 的context。
     */
    public interface Context : EventInterceptor.Context<EventResult> {
        /**
         * 当前被拦截的监听函数实例。
         */
        override val eventContext: EventListenerProcessingContext


        /**
         * 被拦截的监听函数本身。
         */
        public val listener: EventListener get() = eventContext.listener
    }
}

/**
 * 为Java提供的阻塞调用的 [EventListenerInterceptor] 接口方案。
 *
 * @see EventListenerInterceptor
 */
@Api4J
public interface BlockingEventListenerInterceptor : EventListenerInterceptor {

    public fun doIntercept(context: EventListenerInterceptor.Context): EventResult

    @JvmSynthetic
    override suspend fun intercept(context: EventListenerInterceptor.Context): EventResult = doIntercept(context)
}

