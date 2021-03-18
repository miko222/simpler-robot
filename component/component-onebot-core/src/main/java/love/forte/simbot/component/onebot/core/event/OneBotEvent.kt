/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     BaseOneBotEvent.java
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
package love.forte.simbot.component.onebot.core.event

/**
 * one bot事件父类接口。
 *
 *
 *
 * 参考 [onebot 事件概述](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/README.md)
 *
 *
 *
 *
 * > 事件是用户需要从 OneBot 被动接收的数据，有以下几个大类：
 * > 消息事件，包括私聊消息、群消息等
 * > 通知事件，包括群成员变动、好友变动等
 * > 请求事件，包括加群请求、加好友请求等
 * > 元事件，包括 OneBot 生命周期、心跳等
 *
 *
 * 每个事件都有 `time`、`self_id` 和 `post_type` 字段。
 *
 *
 * 其中 post_type 不同字段值表示的事件类型对应如下：
 *
 *
 *  * message：消息事件
 *  * notice：通知事件
 *  * request：请求事件
 *  * meta_event：元事件
 *
 *
 *
 * 其它字段随事件类型不同而有所不同，后面将在事件列表的「事件数据」小标题下给出。
 *
 *
 * 某些字段的值是一些固定的值，在表格的「可能的值」中给出，如果「可能的值」为空则表示没有固定的可能性。
 *
 * @author ForteScarlet
 */
interface OneBotEvent {
    /**
     * 事件发生的时间戳。
     *
     *
     * `number(int64)`
     */
    val time: Long

    /**
     * 收到事件的机器人 QQ 号
     *
     *
     * `number(int64)`
     */
    val selfId: Long


    /**
     * 事件类型。可能的值为 [PostType] 中的元素值。可参考 [onebot事件概述](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/README.md)
     */
    val postType: String

}



/**
 * 通过 [OneBotEvent.postType] 获取一个对应的枚举类型。
 */
public val OneBotEvent.postTypeValue: PostType get() = PostType.byType(postType)