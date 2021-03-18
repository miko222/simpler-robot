/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     FileType.kt
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

package love.forte.simbot.component.onebot.core.segment


/**
 * [消息段][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md] 中，存在的 `file` 参数对应的类型。
 */
enum class FileType(val prefix: String) {

    /** 文件路径 */
    FILE("file://"),

    /** 网络路径 */
    NETWORK("http://"),

    /** base64 */
    BASE64("base64://"),

    /** 其他未知 */
    OTHER("");

    companion object {
        /**
         * 根据不完整前缀判断对应值。
         * 例如 `"file"`、`"http"`。
         */
        @JvmStatic
        fun byPrefix(prefix: String): FileType {
            return values().find { t -> t != OTHER && t.prefix.startsWith(prefix) } ?: OTHER
        }

        /**
         * 根据完整参数判断对应类型。
         * 例如 `"file://D:/img/1.jpg"` 等。
         */
        fun byValue(value: String): FileType {
            return values().find { t -> t != OTHER && value.startsWith(t.prefix) } ?: OTHER
        }

    }
}

