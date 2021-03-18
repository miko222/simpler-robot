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

@file:JvmName("OneBotMessageSegments")

package love.forte.simbot.component.onbot.core.segment

import love.forte.simbot.api.message.containers.AccountCodeContainer
import love.forte.simbot.api.message.containers.GroupCodeContainer


/*
    https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md
 */


/**
 * 存在网络相关的消息段，即有可能存在 `data.cache`、`data.url`等。
 *
 *
 *
 * 一般使用 `0 or 1`表示 `否 或 是`.
 * - 0: false
 * - 1: true
 *
 *
 *
 * 下面所有可能的值为 0 和 1 的参数，也可以使用 no 和 yes、false 和 true。
 *
 */
public interface NetworkAbleSegment {

    /**
     * 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
     */
    val cache: Boolean? get() = true

    /**
     * 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
     */
    val proxy: Boolean? get() = true

    /**
     * 网络路径。
     */
    val url: String?

    /**
     * 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时。
     */
    val timeout: Long
}


/**
 * 存在 `file` 参数的消息段。
 *
 */
public interface FileAbleSegment {
    val file: String
}


/**
 * 通过结果判断文件参数类型。
 */
public val FileAbleSegment.fileType: FileType get() = FileType.byValue(file)


/**
 * 这是一个映射于 [字符串消息][https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/string.md] 的消息段类型，[OneBotStringSegment.Data.value] 中的值应当就是接受到的字符串消息。
 */
public interface OneBotStringSegment : OneBotMessageSegment<OneBotStringSegment.Data> {
    override val type: String
        get() = "string"


    interface Data : SegmentData {
        /**
         * 实际接受到的消息内容。
         */
        val value: String
    }

}


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
public interface OneBotTextSegment : OneBotMessageSegment<OneBotTextSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "text"

    /**
     * 消息段数据
     */
    interface Data : SegmentData {
        /**
         * 纯文本内容。
         */
        val text: String
    }
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
public interface OneBotFaceSegment : OneBotMessageSegment<OneBotFaceSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "face"


    interface Data : SegmentData {
        /**
         * 表情ID。
         */
        val id: String
    }
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
public interface OneBotImageSegment : OneBotMessageSegment<OneBotImageSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "image"


    interface Data : SegmentData, NetworkAbleSegment, FileAbleSegment {
        /**
         * 图片文件名, 一般代表图片的唯一标识，或者文件名、文件路径等。
         * 发送时，支持
         * - `file://`
         * - `http://`
         * - `base64://`
         */
        override val file: String

        /**
         * 网络路径
         */
        override val url: String?


        /**
         * `data.type`。flash 表示闪照，无此参数表示普通图片
         */
        val imageType: String?

        /**
         * 是否为闪照。
         */
        @JvmDefault
        val flash: Boolean
            get() = "flash" == imageType

        /**
         * 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
         */
        override val cache: Boolean get() = true


        /**
         * 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
         */
        override val proxy: Boolean get() = true


        /**
         * 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间，默认不超时。
         */
        override val timeout: Long
    }

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
 * - file | - | 语音文件名
 * - magic | 0 1 | 发送时可选，默认 0，设置为 1 表示变声
 * - url | - | 语音 URL
 * - cache | 0 1 | 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
 * - proxy | 0 1 | 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
 * - timeout | - | 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间 ，默认不超时
 *
 */
public interface OneBotRecordSegment : OneBotMessageSegment<OneBotRecordSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "record"


    interface Data : SegmentData, FileAbleSegment, NetworkAbleSegment {

        override val file: String

        /**
         * 发送时可选，默认 0，设置为 1 表示变声
         */
        val magic: Boolean?

        /**
         * 语音 URL
         */
        override val url: String?


        /**
         * 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
         */
        override val cache: Boolean? get() = true

        /**
         * 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
         */
        override val proxy: Boolean? get() = true


        /**
         * 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间 ，默认不超时.
         */
        override val timeout: Long
    }

}


/**
 * [短视频][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E7%9F%AD%E8%A7%86%E9%A2%91] 消息段。
 * ```
 * {
 *    "type": "video",
 *    "data": {
 *      "file": "http://baidu.com/1.mp4"
 *    }
 *  }
 * ```
 *
 *
 * file | - | 视频文件名
 * url | - | 视频 URL
 * cache | 0 1 | 只在通过网络 URL 发送时有效，表示是否使用已缓存的文件，默认 1
 * proxy | 0 1 | 只在通过网络 URL 发送时有效，表示是否通过代理下载文件（需通过环境变量或配置文件配置代理），默认 1
 * timeout | - | 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间 ，默认不超时
 *
 */
public interface OneBotVideoSegment : OneBotMessageSegment<OneBotVideoSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "video"

    interface Data : SegmentData, FileAbleSegment, NetworkAbleSegment {

        override val file: String

        /**
         * 缓存。
         */
        override val cache: Boolean? get() = true


        override val proxy: Boolean? get() = true


        override val url: String?


        /**
         * 只在通过网络 URL 发送时有效，单位秒，表示下载网络文件的超时时间 ，默认不超时
         */
        override val timeout: Long
    }

}

/**
 * [@某人][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E6%9F%90%E4%BA%BA] 消息段。
 *
 * {
 *  "type": "at",
 *      "data": {
 *          "qq": "10001000"
 *      }
 *  }
 *
 */
public interface OneBotAtSegment : OneBotMessageSegment<OneBotAtSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "at"

    interface Data : SegmentData {

        /**
         * \@的 QQ号，`all` 表示全体成员。
         */
        val code: String
    }

}


/**
 * [猜拳魔法表情][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E7%8C%9C%E6%8B%B3%E9%AD%94%E6%B3%95%E8%A1%A8%E6%83%85]
 * ```
 * {
 *  "type": "rps",
 *     "data": {}
 *  }
 *
 * ```
 *
 *
 */
public interface OneBotRpsSegment : OneBotMessageSegment<OneBotRpsSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "rps"

    override val data: Data get() = Data

    interface Data : SegmentData {
        companion object Empty : Data, SegmentData by SegmentData
    }

}


/**
 * [骰子表情][https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E6%8E%B7%E9%AA%B0%E5%AD%90%E9%AD%94%E6%B3%95%E8%A1%A8%E6%83%85]
 *
 * ```
 * {
 *     "type": "dice",
 *     "data": {}
 * }
 * ```
 *
 */
public interface OneBotDiceSegment : OneBotMessageSegment<OneBotDiceSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "dice"

    override val data: Data get() = Data

    interface Data : SegmentData {
        companion object Empty : Data, SegmentData by SegmentData
    }
}


/**
 * [窗口抖动][https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E7%AA%97%E5%8F%A3%E6%8A%96%E5%8A%A8%E6%88%B3%E4%B8%80%E6%88%B3-]
 * ```
 * {
 *     "type": "shake",
 *     "data": {}
 * }
 * ```
 *
 */
public interface OneBotShakeSegment : OneBotMessageSegment<OneBotShakeSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "shake"

    override val data: Data get() = Data

    interface Data : SegmentData {
        companion object Empty : Data, SegmentData by SegmentData
    }

}


/**
 * [戳一戳][https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#%E6%88%B3%E4%B8%80%E6%88%B3]
 *  ```
 *  {
 *      "type": "poke",
 *      "data": {
 *          "type": "126",
 *          "id": "2003"
 *      }
 *  }
 *  ```
 *
 */
public interface OneBotPokeSegment : OneBotMessageSegment<OneBotPokeSegment.Data> {
    @JvmDefault
    override val type: String
        get() = "poke"


    interface Data : SegmentData {
        /**
         * 戳一戳类型，是`data.type`的值。
         *
         *
         * 参考 [Mirai PokeMessage类][https://github.com/mamoe/mirai/blob/f5eefae7ecee84d18a66afce3f89b89fe1584b78/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L49]
         */
        val type: String

        /**
         * 戳一戳id。
         *
         *
         * 参考 [Mirai PokeMessage类][https://github.com/mamoe/mirai/blob/f5eefae7ecee84d18a66afce3f89b89fe1584b78/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L49]
         *
         */
        val id: String


        /**
         * 表情名。
         *
         *
         * 参考 [Mirai PokeMessage类][https://github.com/mamoe/mirai/blob/f5eefae7ecee84d18a66afce3f89b89fe1584b78/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L49]
         *
         */
        val name: String

    }

}


/**
 * [匿名消息][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E5%8C%BF%E5%90%8D%E5%8F%91%E6%B6%88%E6%81%AF-]
 *
 * ```
 * {
 *     "type": "anonymous",
 *     "data": {}
 * }
 * ```
 *
 */
public interface OneBotAnonymousSegment : OneBotMessageSegment<OneBotAnonymousSegment.Data> {
    override val type: String
        get() = "anonymous"

    override val data: Data
        get() = Data

    interface Data : SegmentData {
        companion object Empty : Data, SegmentData by SegmentData
    }

}


/**
 *
 * [链接分享][https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E9%93%BE%E6%8E%A5%E5%88%86%E4%BA%AB]
 *
 * ```
 * {
 *     "type": "share",
 *     "data": {
 *         "url": "http://baidu.com",
 *         "title": "百度"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * url	✓	✓	-	URL
 * title	✓	✓	-	标题
 * content	✓	✓	-	发送时可选，内容描述
 * image	✓	✓	-	发送时可选，图片 URL
 * ```
 *
 *
 */
public interface OneBotShareSegment : OneBotMessageSegment<OneBotShareSegment.Data> {

    override val type: String
        get() = "share"


    interface Data : SegmentData {
        /**
         * 分享链接
         */
        val url: String

        /**
         * 分享的标题
         */
        val title: String get() = "[分享]"

        /**
         * 发送时可选，内容描述。
         */
        val content: String?

        /**
         * 发送时可选，图片 URL
         */
        val image: String?

    }

}


/**
 *
 * 联系方式推荐
 *
 * - [推荐好友](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E6%8E%A8%E8%8D%90%E5%A5%BD%E5%8F%8B) (See [OneBotGroupContactSegment])
 * - [推荐群](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E6%8E%A8%E8%8D%90%E7%BE%A4) (See [OneBotFriendContactSegment])
 *
 *
 * ### 推荐好友
 * ```
 * {
 *     "type": "contact",
 *     "data": {
 *         "type": "qq",
 *         "id": "10001000"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * type	✓	✓	qq	推荐好友
 * id	✓	✓	-	被推荐人的 QQ 号
 * ```
 *
 * ### 推荐群
 * ```
 * {
 *     "type": "contact",
 *     "data": {
 *         "type": "group",
 *         "id": "100100"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * type	✓	✓	group	推荐群
 * id	✓	✓	-	被推荐群的群号
 * ```
 *
 */
public interface OneBotContactSegment<D : OneBotContactSegment.Data> : OneBotMessageSegment<D> {

    override val type: String
        get() = "contact"

    interface Data : SegmentData {
        /**
         * 推荐类型，例如 `qq` 或 `group`
         */
        val type: String

        /**
         * 推荐信息的id，例如账号或者群号
         */
        val id: String
    }

    /**
     * type默认为 `group`, id代表群号信息, 实现 [GroupCodeContainer]
     */
    interface GroupData : Data, GroupCodeContainer {
        override val type: String
            get() = "group"

        /**
         * Group code is [id].
         */
        override val groupCode: String
            get() = id
    }

    /**
     * type默认为 `qq`, id代表分享人账号信息, 实现 [AccountCodeContainer]
     */
    interface FriendData : Data, AccountCodeContainer {
        override val type: String
            get() = "qq"

        /**
         * Account code is [id].
         */
        override val accountCode: String
            get() = id
    }

}

/**
 * 群分享消息。
 * @see OneBotContactSegment
 */
public interface OneBotGroupContactSegment : OneBotContactSegment<OneBotContactSegment.GroupData>


/**
 * 好友分享消息。
 * @see OneBotContactSegment
 */
public interface OneBotFriendContactSegment : OneBotContactSegment<OneBotContactSegment.FriendData>


/**
 * [位置](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E4%BD%8D%E7%BD%AE)
 * ```
 * {
 *     "type": "location",
 *     "data": {
 *         "lat": "39.8969426",
 *         "lon": "116.3109099"
 *     }
 * }
 * ```
 *
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * lat	✓	✓	-	纬度
 * lon	✓	✓	-	经度
 * title	✓	✓	-	发送时可选，标题
 * content	✓	✓	-	发送时可选，内容描述
 * ```
 *
 */
public interface OneBotLocationSegment : OneBotMessageSegment<OneBotLocationSegment.Data> {
    override val type: String
        get() = "location"


    interface Data : SegmentData {
        /** 纬度 */
        val lat: String

        /** 经度 */
        val lon: String

        /** 发送时可选，标题 */
        val title: String?

        /** 发送时可选，内容描述 */
        val content: String?
    }

}

/**
 * [音乐分享](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E9%9F%B3%E4%B9%90%E5%88%86%E4%BA%AB-)
 * [音乐自定义分享](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E9%9F%B3%E4%B9%90%E8%87%AA%E5%AE%9A%E4%B9%89%E5%88%86%E4%BA%AB-)
 *
 * ### 音乐分享
 *
 * ```
 * {
 *     "type": "music",
 *     "data": {
 *         "type": "163",
 *         "id": "28949129"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * type		✓	qq 163 xm	分别表示使用 QQ 音乐、网易云音乐、虾米音乐
 * id		✓	-	歌曲 ID
 * ```
 *
 *
 * ### 音乐自定义分享
 *
 * ```
 * {
 *     "type": "music",
 *     "data": {
 *         "type": "custom",
 *         "url": "http://baidu.com",
 *         "audio": "http://baidu.com/1.mp3",
 *         "title": "音乐标题"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * type		✓	custom	表示音乐自定义分享
 * url		✓	-	点击后跳转目标 URL
 * audio		✓	-	音乐 URL
 * title		✓	-	标题
 * content		✓	-	发送时可选，内容描述
 * image		✓	-	发送时可选，图片 URL
 * ```
 *
 */
public interface OnebotMusicSegment<D : OnebotMusicSegment.Data> : OneBotMessageSegment<D> {

    override val type: String
        get() = "music"

    interface Data : SegmentData {
        /**
         * 可用 `163`、`qq`等，指明一个具体的音乐类型，并配合 [id] 参数。
         * 可写固定的 `custom` 表示为**自定义音乐分享**并填写 [id]以外的参数。
         */
        val type: String

        /**
         * [type] 不为 `custom` 的时候填写。
         */
        val id: String?

        /**
         * [type] 为 `custom` 的时候填写，点击后跳转目标 URL
         */
        val url: String?

        /**
         * [type] 为 `custom` 的时候填写，音乐 URL
         */
        val audio: String?

        /**
         * [type] 为 `custom` 的时候填写，标题
         */
        val title: String?

        /**
         * [type] 为 `custom` 的时候填写，发送时可选，内容描述
         */
        val content: String?

        /**
         * [type] 为 `custom` 的时候填写，发送时可选，图片 URL
         */
        val image: String?
    }

    /**
     * 有目标类型的音乐数据类型。
     */
    interface TargetData : Data {
        /**
         * 目标类型。
         */
        override val type: String

        /**
         * 有目标类型的时候，需要指定ID
         */
        override val id: String

        override val url: String? get() = null
        override val audio: String? get() = null
        override val title: String? get() = null
        override val content: String? get() = null
        override val image: String? get() = null
    }

    /**
     * 自定义类型的音乐数据类型。
     */
    interface CustomData : Data {
        /**
         * 类型固定为 `custom`
         */
        override val type: String
            get() = "custom"

        /**
         * 为 `custom` 的时候，id一般是不需要的，默认为 `null`。
         */
        override val id: String? get() = null
    }

}

/**
 * 有指定目标的音乐分享。
 */
public interface OneBotTargetMusicSegment : OnebotMusicSegment<OnebotMusicSegment.TargetData>

/**
 * 自定义音乐分享。
 */
public interface OneBotCustomMusicSegment : OnebotMusicSegment<OnebotMusicSegment.CustomData>


/**
 * [回复](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E5%9B%9E%E5%A4%8D)
 *
 * ```
 * {
 *     "type": "reply",
 *     "data": {
 *         "id": "123456"
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * id	✓	✓	-	回复时引用的消息 ID
 * ```
 *
 */
public interface OneBotReplySegment : OneBotMessageSegment<OneBotReplySegment.Data> {

    override val type: String
        get() = "reply"

    interface Data : SegmentData {
        /**
         * 回复时引用的消息ID
         */
        val id: String
    }
}


/**
 * [合并转发](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E5%90%88%E5%B9%B6%E8%BD%AC%E5%8F%91-)
 *
 * ```
 * {
 *     "type": "forward",
 *     "data": {
 *         "id": "123456"
 *     }
 * }
 * ```
 *
 *
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * id	✓		-	合并转发 ID，需通过 get_forward_msg API 获取具体内容
 * ```
 *
 */
public interface OneBotForwardSegment : OneBotMessageSegment<OneBotForwardSegment.Data> {
    override val type: String
        get() = "forward"

    interface Data : SegmentData {
        /**
         * 合并转发 ID，需通过 get_forward_msg API 获取具体内容
         */
        val id: String
    }

}


/**
 *
 * ## 合并转发节点
 * [合并转发节点](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E5%90%88%E5%B9%B6%E8%BD%AC%E5%8F%91%E8%8A%82%E7%82%B9-)
 *
 * ```
 * {
 *     "type": "node",
 *     "data": {
 *         "id": "123456"
 *     }
 * }
 * ```
 *
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * id		✓	-	转发的消息 ID
 * ```
 *
 *
 * ## 合并转发自定义节点
 * [合并转发自定义节点](https://github.com/howmanybots/onebot/blob/master/v11/specs/message/segment.md#%E5%90%88%E5%B9%B6%E8%BD%AC%E5%8F%91%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8A%82%E7%82%B9)
 *
 * 例1：
 * ```
 * {
 *     "type": "node",
 *     "data": {
 *         "user_id": "10001000",
 *         "nickname": "某人",
 *         "content": "[CQ:face,id=123]哈喽～"
 *     }
 * }
 * ```
 *
 * 例2：
 * ```
 * {
 *     "type": "node",
 *     "data": {
 *         "user_id": "10001000",
 *         "nickname": "某人",
 *         "content": [
 *             {"type": "face", "data": {"id": "123"}},
 *             {"type": "text", "data": {"text": "哈喽～"}}
 *         ]
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * user_id	✓	✓	-	发送者 QQ 号
 * nickname	✓	✓	-	发送者昵称
 * content	✓	✓	-	消息内容，支持发送消息时的 message 数据类型，见 API 的参数
 * ```
 *
 *
 */
public interface OneBotForwardNodeSegment : OneBotMessageSegment<OneBotForwardNodeSegment.Data> {

    override val type: String
        get() = "node"

    /**
     * 如果 [id] 不为null，则认为是通过指定节点进行转发，即等同于 [NormalData],
     * 如果 [id] 为null，则认为是[自定义节点转发][CustomData]。
     * @see NormalData
     * @see CustomData
     */
    interface Data : SegmentData {
        /**
         * 转发的消息 ID
         */
        val id: String?

        /**
         * 发送者 QQ 号
         * 自定义节点才会存在。
         */
        val userId: String?

        /**
         * 发送者昵称
         * 自定义节点才会存在。
         */
        val nickname: String?

        /**
         *
         * 消息正文, 是一个 [消息段][OneBotMessageSegment]
         */
        val constant: OneBotMessageSegment<*>?

    }

    /**
     * 普通的转发节点
     */
    interface NormalData : Data {
        /**
         * 转发的消息 ID
         */
        override val id: String


        /**
         * 发送者 QQ 号
         * 自定义节点才会存在。
         */
        override val userId: String? get() = null

        /**
         * 发送者昵称
         * 自定义节点才会存在。
         */
        override val nickname: String? get() = null

        /**
         *
         * 消息正文, 是一个 [消息段][OneBotMessageSegment]
         */
        override val constant: OneBotMessageSegment<*>? get() = null
    }

    /**
     * 自定义转发节点数据
     */
    interface CustomData : Data {
        /**
         * 转发的消息 ID
         */
        override val id: String? get() = null

        /**
         * 发送者 QQ 号
         * 自定义节点才会存在。
         */
        override val userId: String

        /**
         * 发送者昵称
         * 自定义节点才会存在。
         */
        override val nickname: String

        /**
         *
         * 消息正文, 是一个 [消息段][OneBotMessageSegment]
         */
        override val constant: OneBotMessageSegment<*>
    }

}


/**
 *
 * [XML 消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#xml-%E6%B6%88%E6%81%AF)
 *
 * ```
 * {
 *     "type": "xml",
 *     "data": {
 *         "data": "<?xml ..."
 *     }
 * }
 * ```
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * data	✓	✓	-	XML 内容
 * ```
 */
public interface OneBotXmlSegment : OneBotMessageSegment<OneBotXmlSegment.Data> {
    override val type: String
        get() = "xml"

    interface Data : SegmentData {
        /**
         * xml最终的值。
         */
        val data: String
    }
}

/**
 * [JSON消息](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md#json-%E6%B6%88%E6%81%AF)
 *
 * ```
 * {
 *     "type": "json",
 *     "data": {
 *         "data": "{\"app\": ..."
 *     }
 * }
 * ```
 *
 *
 * ```
 * 参数名	收	发	可能的值	说明
 * data	✓	✓	-	JSON 内容
 * ```
 *
 */
public interface OneBotJsonSegment : OneBotMessageSegment<OneBotJsonSegment.Data> {

    override val type: String
        get() = "json"

    interface Data : SegmentData {
        /**
         * JSON 内容
         */
        val data: String
    }

}









