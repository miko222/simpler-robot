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
 * 存在缓存值的消息段，即有可能存在 `data.cache`。
 * 一般使用 `0 or 1`表示 `否 或 是`.
 * - 0: false
 * - 1: true
 *
 * 下面所有可能的值为 0 和 1 的参数，也可以使用 no 和 yes、false 和 true。
 *
 */
public interface CacheableSegment {
    /**
     * 缓存值。
     */
    val cache: Boolean
}


/**
 * 存在 `file` 参数的消息段。
 *
 */
public interface FileAbleSegment {
    val file: String
}


public val FileAbleSegment.fileType: FileType get() = FileType.byPrefix(file)



/**
 * [纯文本消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E7%BA%AF%E6%96%87%E6%9C%AC)
    ```
    {
        "type": "text",
        "data": {
        "text": "纯文本内容"
        }
    }
```
 */
public interface OneBotTextSegment : OneBotMessageSegment {
    @JvmDefault
    override val type: String
        get() = "text"

    /**
     * 纯文本内容。
     */
    val text: String
}


/**
 * [表情消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#qq-%E8%A1%A8%E6%83%85)
 * ```
    {
    "type": "face",
        "data": {
        "id": "123"
        }
    }
 * ```
 */
public interface OneBotFaceSegment : OneBotMessageSegment {
    @JvmDefault
    override val type: String
        get() = "face"

    /**
     * 表情ID。
     */
    val id: String
}


/**
 *
 * [图片消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#qq-%E8%A1%A8%E6%83%85)
 *
 * ```
 * {
 *     "type": "image",
 *     "data": {
 *         "file": "http://baidu.com/1.jpg"
 *     }
 * }
 * ```
 *
 *
 *
 * - file 图片文件名
 *
 * - type | `flash` | 图片类型，flash 表示闪照，无此参数表示普通图片
 * - url | `-` |  图片 URL
 * - cache | `0 1` | 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
 * - proxy | `0 1` | 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
 * - timeout | `-` | 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时
 *
 *
 */
public interface OneBotImageSegment : OneBotMessageSegment, CacheableSegment {
    @JvmDefault
    override val type: String
        get() = "image"

    /**
     * 图片文件名, 一般代表图片的唯一标识，或者文件名、文件路径等。
     * 发送时，支持
     * - `file://`
     * - `http://`
     * - `base64://`
     */
    val file: String


    /**
     * `data.type`。flash 表示闪照，无此参数表示普通图片
     */
    val imageType: String?

    /**
     * 是否为闪照。
     */
    @JvmDefault
    val flash: Boolean get() = imageType == "flash"

    /**
     * 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
     */
    @JvmDefault
    override val cache: Boolean get() = true


    /**
     * 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
     */
    val proxy: Boolean get() = true


    /**
     * 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时。
      */
    val timeout: Long

}


/**
 * [语言消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E8%AF%AD%E9%9F%B3)
 * ```
 * {
 *     "type": "record",
 *     "data": {
 *         "file": "http://baidu.com/1.mp3"
 *     }
 * }
 * ```
 *
 */
public interface OneBotRecordSegment : OneBotMessageSegment {
    @JvmDefault
    override val type: String
        get() = "record"

    val file: String


}




