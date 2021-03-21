/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     BaseSimOneMsg.kt
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

package love.forte.simbot.component.onebot.core.cross.message

import love.forte.simbot.api.message.containers.BotInfo
import love.forte.simbot.api.message.events.MessageGet
import love.forte.simbot.component.onebot.core.cross.toBotInfo
import love.forte.simbot.component.onebot.core.event.message.OneBotMessage


/**
 * [simbot 消息事件][MessageGet] 与 [onebot 消息事件][OneBotMessage]的整合抽象类。
 *
 */
public abstract class BaseSimOneMessage<S : OneBotMessage.MessageEventSubType> : MessageGet, OneBotMessage<S> {

    override val id: String
        get() = messageId

    /**
     * bot信息
     */
    // TODO bot name.
    override val botInfo: BotInfo = this.toBotInfo("TODO")


    /**
     * 得到原始数据字符串。
     */
    override val originalData: String
        get() = rawMessage

    /**
     * 事件时间。
     */
    abstract override val time: Long


}