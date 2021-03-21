/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     OneBotMessageContent.kt
 *  *
 *  * You can contact the author through the following channels:
 *  * github https://github.com/ForteScarlet
 *  * gitee  https://gitee.com/ForteScarlet
 *  * email  ForteScarlet@163.com
 *  * QQ     1149159218
 *  *
 *  *
 *
 */

@file:JvmName("OneBotMessageContents")
package love.forte.simbot.component.onebot.core.cross.message

import catcode.Neko
import love.forte.simbot.api.message.MessageContent
import love.forte.simbot.api.message.emptyContent
import love.forte.simbot.component.onebot.core.segment.OneBotMessageSegment


public fun messageContent(segments: List<OneBotMessageSegment<*>>): MessageContent {
    return when {
        segments.isEmpty() -> emptyContent()
        segments.size == 1 -> SingletonOneBotMessageContent(segments[0])
        else -> TODO()
    }
}




/**
 * onebot的消息正文实例, 通过 [OneBotMessageSegment] 作为消息容器内容。
 * 此代表仅存一个独立的[OneBotMessageSegment].
 */
private class SingletonOneBotMessageContent(private val segments: OneBotMessageSegment<*>): MessageContent {
    /**
     * 消息字符串文本。
     *
     * 一般来讲，[msg] 就相当于将 [cats] 中的内容全部toString并拼接在了一起，但是text文本不再表现为cat码。
     */
    override val msg: String
        get() = TODO("Not yet implemented")

    /**
     * 需要重写equals。
     */
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }


    override fun hashCode(): Int {
        return segments.hashCode()
    }


    /**
     * 获取此消息中的所有可能包含的cat码。
     */
    override val cats: List<Neko>
        get() = TODO("Not yet implemented")
}