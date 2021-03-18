/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     OneBotMessageEvent.kt
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

package love.forte.simbot.component.onbot.core.event.message

import love.forte.simbot.component.onbot.core.event.OneBotEvent
import love.forte.simbot.component.onbot.core.event.SubTypeContainer


/**
 *
 * onebot 消息类型事件接口。
 *
 * 可参考 [消息事件](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/message.md)
 *
 */
public interface OneBotMessageEvent : OneBotEvent, SubTypeContainer {

    /**
     * 消息类型。
     *
     * [消息事件](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/message.md)中的两个消息事件均存在此属性。
     * 目前来看，可能存在的值为:
     * - `private`
     * - `group`
     *
     */
    val messageType: String


    /**
     *
     * 消息事件子类型。
     *
     *
     *
     * 可能的值有：
     *
     * - 私聊消息 (private)
     *    - `friend`
     *    - `group`
     *    - `other`
     *
     * - 群消息 (group)
     *    - `normal`
     *    - `anonymous`
     *    - `notice`
     *
     *
     */
    override val subType: String

}