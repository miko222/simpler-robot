/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     SegmentCatParseUtil.kt
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

@file:JvmName("SegmentCatParseUtil")
package love.forte.simbot.component.onebot.core.segment

import catcode.Neko
import catcode.NekoAibo

/**
 * 将一个 [OneBotMessageSegment] 转化为 [Neko].
 */
public fun OneBotMessageSegment<*>.toNeko(util: NekoAibo): Neko {

    util.getNekoBuilder(type, true)


    TODO()
}







