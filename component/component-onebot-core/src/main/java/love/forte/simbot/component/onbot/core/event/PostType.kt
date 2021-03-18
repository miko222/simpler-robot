/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     PostType.java
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
package love.forte.simbot.component.onbot.core.event

import java.util.*

/**
 * onebot事件中的 `post_type`。
 *
 *
 * post_type 不同字段值表示的事件类型对应如下：
 *
 *
 *  * message：消息事件
 *  * notice：通知事件
 *  * request：请求事件
 *  * meta_event：元事件
 *
 * 可参考 [事件概述](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/README.md)
 *
 * @author ForteScarlet
 */
enum class PostType(val type: String) {

    /** 消息事件  */
    MESSAGE(Constant.MESSAGE_TYPE),

    /** 通知事件  */
    NOTICE(Constant.NOTICE_TYPE),

    /** 请求事件  */
    REQUEST(Constant.REQUEST_TYPE),

    /** 元事件  */
    META_EVENT(Constant.META_EVENT_TYPE);


    private object Constant {
        internal const val MESSAGE_TYPE = "message"
        internal const val NOTICE_TYPE = "notice"
        internal const val REQUEST_TYPE = "request"
        internal const val META_EVENT_TYPE = "meta_event"
    }


    companion object {
        /**
         * 通过一个 `post_type` 取值范围内的type字符串获取一个对应的枚举元素。
         * @param type post_type value.
         * @return PostType.
         */
        @JvmStatic
        fun byType(type: String?): PostType {
            return when (type) {
                Constant.MESSAGE_TYPE -> MESSAGE
                Constant.NOTICE_TYPE -> NOTICE
                Constant.REQUEST_TYPE -> REQUEST
                Constant.META_EVENT_TYPE -> META_EVENT
                else -> throw NoSuchElementException(type)
            }
        }
    }
}