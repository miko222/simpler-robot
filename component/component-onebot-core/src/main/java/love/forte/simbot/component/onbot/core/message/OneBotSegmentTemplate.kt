/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     OneBotSegmentTemplate.kt
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

@file:JvmName("OneBotMessageSegmentTemplate")
package love.forte.simbot.component.onbot.core.message


/*
    https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md
 */

/**
 * [纯文本消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E7%BA%AF%E6%96%87%E6%9C%AC)
 */
public interface OneBotTextSegment : OneBotMessageSegment {
    override val type: String
        get() = "text"

    /**
     * 纯文本内容。
     */
    val text: String
}


public interface OneBotFaceSegment : OneBotMessageSegment {
    override val type: String
        get() = "face"
}
