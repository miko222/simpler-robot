/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     QICQ.kt
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

/*
    一些与qq相关的方法。
 */
@file:JvmName("QICQ")
package love.forte.simbot.component.onebot.core.qicq


/**
 * 用户头像。
 */
public fun userAvatar(id: Long): String = "http://q1.qlogo.cn/g?b=qq&nk=$id&s=640"

/**
 * 群头像。
 */
public fun groupAvatar(id: Long): String = "https://p.qlogo.cn/gh/$id/$id/640"

