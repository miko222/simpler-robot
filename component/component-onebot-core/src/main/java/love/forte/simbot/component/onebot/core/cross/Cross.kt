/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     Cross.kt
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

@file:JvmName("SimOneBotCross")
package love.forte.simbot.component.onebot.core.cross

import love.forte.simbot.api.message.containers.AccountInfo
import love.forte.simbot.api.message.containers.BotInfo
import love.forte.simbot.api.message.containers.GroupAccountInfo
import love.forte.simbot.component.onebot.core.OneBotRuntimeException
import love.forte.simbot.component.onebot.core.event.OneBotEvent
import love.forte.simbot.component.onebot.core.event.message.OneBotGroupMessage
import love.forte.simbot.component.onebot.core.event.message.OneBotMessage
import love.forte.simbot.component.onebot.core.qicq.userAvatar


public class OneBotCrossException : OneBotRuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace)
}


@Suppress("NOTHING_TO_INLINE")
inline fun crossException(message: String?): Nothing = throw OneBotCrossException(message)


/*
    onebot与simbot的交汇
 */

/**
 * 通过[sender][S]构建一个 [AccountInfo]
 */
fun <S : OneBotMessage.Sender> S.asAccountInfo(): AccountInfo = SenderAccountInfo(this)

/**
 * 通过[sender][S]构建一个 [GroupAccountInfo]
 */
fun <S : OneBotGroupMessage.Sender> S.asGroupAccountInfo(): GroupAccountInfo = SenderGroupAccountInfo(this)


/**
 * [AccountInfo] delegate by [sender][OneBotMessage.Sender].
 */
private data class SenderAccountInfo(
    private val sender: OneBotMessage.Sender,
) : AccountInfo {
    /**
     * QQ头像
     */
    override val accountAvatar: String? = sender.userId?.let { id -> userAvatar(id) }

    /**
     * 账号
     */
    override val accountCode: String
        get() = sender.userId?.toString()
            ?: crossException("Cannot get userId from delegate sender [$sender].")

    /**
     * 昵称。可能会出现为null的情况。
     */
    override val accountNickname: String?
        get() = sender.nickname

    /** 好友备注或群名片。可能为null。 */
    override val accountRemark: String?
        get() = if (sender is OneBotGroupMessage.Sender) sender.card else null
}

/**
 * [GroupAccountInfo] delegate by [groupSender][OneBotGroupMessage.Sender].
 */
private data class SenderGroupAccountInfo(
    private val sender: OneBotGroupMessage.Sender,
) : GroupAccountInfo {
    /**
     * QQ头像.
     */
    override val accountAvatar: String? = sender.userId?.let { id -> userAvatar(id) }

    override val accountCode: String
        get() = sender.userId?.toString() ?: crossException("Cannot get userId from delegate sender [$sender].")

    override val accountNickname: String?
        get() = sender.nickname

    override val accountRemark: String?
        get() = sender.card

    override val accountTitle: String?
        get() = sender.title
}

/**
 * [OneBotEvent] to [BotInfo].
 * Need bot name.
 */
fun OneBotEvent.toBotInfo(botName: String): BotInfo = BotInfoData(userAvatar(selfId), selfId.toString(), botName)


/**
 * OneBot
 */
private data class BotInfoData(
    override val botAvatar: String?,
    override val botCode: String,
    override val botName: String
) : BotInfo



