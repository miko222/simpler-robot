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

import kotlinx.coroutines.Deferred
import love.forte.simbot.Attribute
import love.forte.simbot.AttributeContainer
import love.forte.simbot.ID
import love.forte.simbot.PriorityConstant


/**
 *
 * 一个监听事件的事件监听器。
 *
 * 事件监听器监听到实现并进行逻辑处理。此处不包含诸如过滤器等内容。
 *
 * 事件监听器存在 [优先级][priority]，默认优先级为 [Int.MAX_VALUE].
 *
 * @author ForteScarlet
 */
public interface EventListener : java.util.EventListener, AttributeContainer {

    /**
     * 监听器必须是唯一的. 通过 [id] 进行唯一性确认。
     */
    public val id: ID

    /**
     * 优先级。对于一次事件处理流程，所有监听函数会根据此优先级进行顺序处理。
     * 整个流程下的所有监听函数中，[isAsync] == true 的监听函数会比普通函数有更高的优先级。
     */
    public val priority: Int get() = PriorityConstant.NORMAL

    /**
     * 是否需要异步执行。
     *
     * 异步执行的监听函数会被异步执行并立即返回一个 [Deferred], 并将其作为 [AsyncEventResult] 提供给当前的事件处理上下文中。
     *
     * 默认情况下，异步函数无法通过 [EventResult.isTruncated] 截断后续函数。
     *
     * 当 `isAsync == true` 时，当前监听函数在 [EventProcessor] 中被调度的实际顺序会高于 `isAsync == false` 的函数，
     * 也就是说当一次事件被推送的时候，会优先启动所有的异步监听函数。
     *
     * 理所当然的, 当监听函数被标记为 [isAsync] 时，其对应的所有 [监听函数拦截器][EventListenerInterceptor] 也应当相同的被异步化，并会拦截真正的监听函数结果。
     * 而 [监听事件拦截器][EventProcessingInterceptor] 不会受到影响。
     *
     */
    public val isAsync: Boolean

    /**
     * 判断当前监听函数是否对可以对指定的事件进行监听。
     *
     */
    public fun isTarget(eventType: Event.Key<*>): Boolean


    /**
     * 监听函数可以允许存在其独特的属性。
     */
    override fun <T : Any> getAttribute(attribute: Attribute<T>): T? = null


    /**
     * 监听函数的事件执行逻辑。
     *
     * 通过 [EventProcessingContext] 处理事件，完成处理后返回 [处理结果][EventResult].
     *
     */
    @JvmSynthetic
    public suspend operator fun invoke(context: EventProcessingContext): EventResult
}
