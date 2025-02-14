/*
 *  Copyright (c) 2021-2022 ForteScarlet <ForteScarlet@163.com>
 *
 *  本文件是 simply-robot (或称 simple-robot 3.x 、simbot 3.x ) 的一部分。
 *
 *  simply-robot 是自由软件：你可以再分发之和/或依照由自由软件基金会发布的 GNU 通用公共许可证修改之，无论是版本 3 许可证，还是（按你的决定）任何以后版都可以。
 *
 *  发布 simply-robot 是希望它能有用，但是并无保障;甚至连可销售和符合某个特定的目的都不保证。请参看 GNU 通用公共许可证，了解详情。
 *
 *  你应该随程序获得一份 GNU 通用公共许可证的复本。如果没有，请看:
 *  https://www.gnu.org/licenses
 *  https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *  https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *
 *
 */

package love.forte.simbot.event

import love.forte.simbot.message.doSafeCast
import love.forte.simbot.utils.runInBlocking


/**
 * 一个 **起点** 事件。
 *
 * [StartPointEvent] 是一个变化的起始变化，通常情况下其代表在变化后变化体开始存在，
 * 因此在 [StartPointEvent] 中 [before] 通常为 null。
 */
public interface StartPointEvent<S, V> : ChangedEvent<S, V?, V> {
    /**
     * 此次变化的目标。
     */
    public suspend fun target(): V

    override suspend fun source(): S
    override suspend fun before(): V? = null
    override suspend fun after(): V = target()

    override val before: V? get() = null
    override val after: V get() = target
    public val target: V get() = runInBlocking { target() }

    public companion object Key : BaseEventKey<StartPointEvent<*, *>>("api.start_point", ChangedEvent) {
        override fun safeCast(value: Any): StartPointEvent<*, *>? = doSafeCast(value)
    }
}


/**
 * 一个 **终点** 事件。
 *
 *  [EndPointEvent] 是一个变化的最终变化，通常情况下其代表在变化后变化体则不再存在，
 * 因此在 [EndPointEvent] 中，[after] 应为null。
 *
 */
public interface EndPointEvent<S, V> : ChangedEvent<S, V, V?> {
    /**
     * 此次变化的目标。
     */
    public suspend fun target(): V

    override suspend fun before(): V = target()
    override suspend fun after(): V? = null

    override val before: V get() = target
    override val after: V? get() = null
    public val target: V get() = runInBlocking { target() }

    public companion object Key : BaseEventKey<EndPointEvent<*, *>>("api.end_point", ChangedEvent) {
        override fun safeCast(value: Any): EndPointEvent<*, *>? = doSafeCast(value)
    }

}


/**
 * 一个 **增加** 事件，代表某种 [事物][target] 被增加到了一个 [源][source] 中。
 *
 *
 */
public interface IncreaseEvent<S, V> : StartPointEvent<S, V> {
    override suspend fun source(): S
    override suspend fun target(): V

    public companion object Key : BaseEventKey<IncreaseEvent<*, *>>("api.increase", StartPointEvent) {
        override fun safeCast(value: Any): IncreaseEvent<*, *>? = doSafeCast(value)
    }
}


/**
 * 一个 **减少** 事件，代表某种 [事物][target] 被从一个 [源][source] 中移除。
 *
 */
public interface DecreaseEvent<S, V> : EndPointEvent<S, V> {
    override suspend fun source(): S
    override suspend fun target(): V

    public companion object Key : BaseEventKey<DecreaseEvent<*, *>>("api.decrease", EndPointEvent) {
        override fun safeCast(value: Any): DecreaseEvent<*, *>? = doSafeCast(value)
    }
}