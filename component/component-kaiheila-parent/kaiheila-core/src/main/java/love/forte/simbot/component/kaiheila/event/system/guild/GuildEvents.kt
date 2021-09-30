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

@file:JvmName("GuildEvents")
package love.forte.simbot.component.kaiheila.event.system.guild

import love.forte.simbot.component.kaiheila.event.Event


/**
 *
 *
 */
// public interface GuildEventExtra<B : GuildEventExtraBody> : Event.Extra.Sys<B>


/**
 *
 * [服务器相关事件列表](https://developer.kaiheila.cn/doc/event/guild)
 *
 * @see Event.Extra.Sys.body
 */
public interface GuildEventExtraBody : Event.Extra.Sys.Body


public interface GuildEventExtra<B : GuildEventExtraBody> : Event.Extra.Sys<B>