/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     SimOBPrivateMessage.kt
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

import love.forte.simbot.api.message.MessageContent
import love.forte.simbot.api.message.assists.Flag
import love.forte.simbot.api.message.containers.AccountInfo
import love.forte.simbot.api.message.containers.BotInfo
import love.forte.simbot.api.message.events.PrivateMsg
import love.forte.simbot.component.onebot.core.cross.asAccountInfo
import love.forte.simbot.component.onebot.core.event.message.OneBotPrivateMessage


/**
 * 整合 [simbot privateMsg][PrivateMsg] 与 [onebot privateMsg][OneBotPrivateMessage]
 * 的私聊消息接口。
 */
public interface SimOnePrivateMsg : OneBotPrivateMessage, PrivateMsg {

    /**
     * 账号的信息。
     */
    override val accountInfo: AccountInfo
        get() = sender.asAccountInfo()

    /**
     * bot信息
     */
    override val botInfo: BotInfo
        get() = TODO("Not yet implemented")

    /**
     * 得到原始数据字符串。
     */
    override val originalData: String
        get() = TODO("Not yet implemented")

    /**
     *  消息事件的消息正文文本。
     */
    override val msgContent: MessageContent


    /** 当前监听事件消息的ID。一般情况下应当是一个唯一ID。 */
    override val id: String
        get() = TODO("Not yet implemented")

    /** 消息接收到的时间。一般是一个毫秒时间戳。 */
    override val time: Long
        get() = TODO("Not yet implemented")


    /**
     * 私聊消息标识
     *
     * 一般可用于撤回之类的。默认为ID的值。
     *
     * 如果只是使用 [id] 作为flag载体，在实现的时候可以参考 [PrivateMsgIdFlagContent]
     *
     */
    override val flag: Flag<PrivateMsg.FlagContent>
        get() = TODO("Not yet implemented")

    /**
     * 获取私聊消息类型
     */
    override val privateMsgType: PrivateMsg.Type
        get() = TODO("Not yet implemented")
}


// /**
//  * [SimOBPrivateMsg] 接口实现。
//  */
// public class SimOBPrivateMsgImpl : SimOBPrivateMsg {
//
//
//
// }
