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

package love.forte.simboot.listener

import love.forte.di.BeanContainer
import love.forte.simbot.PriorityConstant
import love.forte.simbot.event.Event
import love.forte.simbot.event.EventListener
import love.forte.simbot.event.EventListenerRegistrar
import kotlin.reflect.KClass
import kotlin.reflect.KFunction


/**
 *
 * [love.forte.simboot.annotation.Listener] 注解所标记的监听函数处理器。
 *
 * 处理器可以存在多个，所有处理器将会按照 [priority] 进行顺序处理解析。
 *
 * [ListenerAnnotationProcessor] 所注册的监听函数通常情况喜下不需要关心对过滤器的处理，
 * 对于一个监听函数，在函数本体解析完成后进行监听函数过滤器的解析。
 *
 * @see love.forte.simboot.annotation.Listener
 *
 * @author ForteScarlet
 */
public interface ListenerAnnotationProcessor {

    /**
     * 优先级。
     */
    public val priority: Int get() = PriorityConstant.NORMAL

    /**
     * 提供 [ListenerAnnotationProcessorContext], 进行处理并向 [ListenerAnnotationProcessorContext.listenerRegistrar] 中注册监听函数。
     *
     * @return 如果返回true，则会继续此处理器后续的处理器，返回false将会终止处理。
     *
     */
    public fun process(context: ListenerAnnotationProcessorContext): Boolean

}


/**
 * [ListenerAnnotationProcessor] 进行处理所需参数集.
 */
public interface ListenerAnnotationProcessorContext {
    /**
     * 监听函数注解实例对象
     */
    public val listenerData: ListenerData

    /**
     * 如果能够获取，将会提供一个当前函数所属的bean的id。
     */
    public val beanId: String?

    /**
     * 此监听函数被获取到的所属类实例。
     */
    public val from: KClass<*>?

    /**
     * 可以寻找所有的binder factory的容器。
     */
    public val binderFactoryContainer: ParameterBinderFactoryContainer

    /**
     * 此监听函数对应的function。
     */
    public val function: KFunction<*>

    /**
     * Bean容器。
     */
    public val beanContainer: BeanContainer

    /**
     * 监听函数注册器。
     */
    public val listenerRegistrar: EventListenerRegistrar
}


/**
 * 监听函数注册后置处理器。当 [ListenerAnnotationProcessor] 进行监听函数处理并且通过其参数 [EventListenerRegistrar] 进行注册时，
 * 对这个被注册的监听函数进行后置处理.
 *
 * TODO
 */
public interface ListenerAnnotationPostRegisteredProcessor {


    /**
     * 当一个注解监听器被解析并注册后，执行此后置处理器对其进行处理，并得到最终结果，
     * 或者返回 null 抛弃本次注册行为。
     */
    public fun process(listenerData: ListenerData, listener: EventListener): EventListener?
}


/**
 * [love.forte.simboot.annotation.Listen] 对应的数据类。
 * @see love.forte.simboot.annotation.Listen
 */
public data class ListenData(
    val value: KClass<out Event>
)

/**
 * [love.forte.simboot.annotation.Listens] 对应的数据类。
 * @see love.forte.simboot.annotation.Listens
 */
public data class ListensData(
    val value: List<ListenData>
)


/**
 * [love.forte.simboot.annotation.Listener] 对应的数据类。
 * @see love.forte.simboot.annotation.Listener
 */
public data class ListenerData(
    val id: String, // empty able
    val priority: Int,
    val async: Boolean,
    val listens: ListensData?,
)