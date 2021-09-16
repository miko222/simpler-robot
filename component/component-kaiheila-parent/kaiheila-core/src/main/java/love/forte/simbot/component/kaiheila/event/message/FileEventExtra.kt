/*
 *
 *  * Copyright (c) 2021. ForteScarlet All rights reserved.
 *  * Project  simple-robot
 *  * File     MiraiAvatar.kt
 *  *
 *  * You can contact the author through the following channels:
 *  * github https://github.com/ForteScarlet
 *  * gitee  https://gitee.com/ForteScarlet
 *  * email  ForteScarlet@163.com
 *  * QQ     1149159218
 *
 */

package love.forte.simbot.component.kaiheila.event.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.component.kaiheila.objects.Attachments
import love.forte.simbot.component.kaiheila.objects.Role
import love.forte.simbot.component.kaiheila.objects.User


/**
 * [文件消息](https://developer.kaiheila.cn/doc/event/message#%E6%96%87%E4%BB%B6%E6%B6%88%E6%81%AF)
 * @author ForteScarlet
 */
@Serializable
public data class FileEventExtra(
    override val type: Int,
    @SerialName("guild_id")
    override val guildId: String,
    @SerialName("channel_name")
    override val channelName: String = "",
    override val author: User,

    /**
     * unknown field.
     */
    val code: String,

    /**
     * 附件
     */
    val attachments: Attachments,

    ) : MessageEventExtra {
    override val mention: List<String> get() = emptyList()
    override val mentionAll: Boolean get() = false
    override val mentionRoles: List<Role> get() = emptyList()
    override val mentionHere: Boolean get() = false
}
