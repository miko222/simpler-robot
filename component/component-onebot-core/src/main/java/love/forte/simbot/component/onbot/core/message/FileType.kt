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

package love.forte.simbot.component.onbot.core.message


/**
 * 消息段中，存在的 `file` 参数对应的类型。
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
        @JvmStatic
         fun byPrefix(file: String): FileType {
            values().forEach { type ->
                if (type != OTHER && file.startsWith(type.prefix)) {
                    return@byPrefix type
                }
            }
            return OTHER
        }
     }
}

