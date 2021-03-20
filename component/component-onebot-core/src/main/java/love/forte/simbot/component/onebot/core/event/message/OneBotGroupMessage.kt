/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     OneBotGroupMsg.kt
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

package love.forte.simbot.component.onebot.core.event.message

import love.forte.simbot.component.onebot.core.segment.OneBotMessageSegment

/**
 * [群聊消息事件](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/message.md#%E7%BE%A4%E6%B6%88%E6%81%AF)
 *
 * ```
 * 事件数据
 * 字段名	数据类型	可能的值	说明
 * time	number (int64)	-	事件发生的时间戳
 * self_id	number (int64)	-	收到事件的机器人 QQ 号
 * post_type	string	message	上报类型
 * message_type	string	group	消息类型
 * sub_type	string	normal、anonymous、notice	消息子类型，正常消息是 normal，匿名消息是 anonymous，系统提示（如「管理员已禁止群内匿名聊天」）是 notice
 * message_id	number (int32)	-	消息 ID
 * group_id	number (int64)	-	群号
 * user_id	number (int64)	-	发送者 QQ 号
 * anonymous	object	-	匿名信息，如果不是匿名消息则为 null
 * message	message	-	消息内容
 * raw_message	string	-	原始消息内容
 * font	number (int32)	-	字体
 * sender	object	-	发送人信息
 * 其中 anonymous 字段的内容如下：
 *
 * 字段名	数据类型	说明
 * id	number (int64)	匿名用户 ID
 * name	string	匿名用户名称
 * flag	string	匿名用户 flag，在调用禁言 API 时需要传入
 * sender 字段的内容如下：
 *
 * 字段名	数据类型	说明
 * user_id	number (int64)	发送者 QQ 号
 * nickname	string	昵称
 * card	string	群名片／备注
 * sex	string	性别，male 或 female 或 unknown
 * age	number (int32)	年龄
 * area	string	地区
 * level	string	成员等级
 * role	string	角色，owner 或 admin 或 member
 * title	string	专属头衔
 * 需要注意的是，sender 中的各字段是尽最大努力提供的，也就是说，不保证每个字段都一定存在，也不保证存在的字段都是完全正确的（缓存可能过期）。尤其对于匿名消息，此字段不具有参考价值。
 *
 * 快速操作
 * 字段名	数据类型	说明	默认情况
 * 字段名	数据类型	说明	默认情况
 * reply	message	要回复的内容	不回复
 * auto_escape	boolean	消息内容是否作为纯文本发送（即不解析 CQ 码），只在 reply 字段是字符串时有效	不转义
 * at_sender	boolean	是否要在回复开头 at 发送者（自动添加），发送者是匿名用户时无效	at 发送者
 * delete	boolean	撤回该条消息	不撤回
 * kick	boolean	把发送者踢出群组（需要登录号权限足够），不拒绝此人后续加群请求，发送者是匿名用户时无效	不踢
 * ban	boolean	把发送者禁言 ban_duration 指定时长，对匿名用户也有效	不禁言
 * ban_duration	number	禁言时长	30 分钟
 * ```
 *
 *
 */
interface OneBotGroupMessage : OneBotMessage<OneBotGroupMessage.SubType> {

    /**
     * 事件发生的时间戳
     */
    override val time: Long

    /**
     * 收到消息的bot信息
     */
    override val selfId: Long

    /**
     * 消息类型
     */
    override val messageType: String
        get() = "group"


    /**
     * 消息ID
     */
    override val messageId: String


    /**
     * 发送者QQ号。
     */
    override val userId: Long


    /**
     * 群号
     */
    val groupId: Long

    /**
     * 消息，或消息段。
     */
    override val message: OneBotMessageSegment<*>

    /**
     * 原始消息内容
     */
    override val rawMessage: String


    /**
     * 匿名信息，如果不是匿名消息则为 null
     */
    val anonymous: Anonymous?


    /**
     * 匿名消息实体.
     *
     * ```
     * 字段名	数据类型	说明
     * id	number (int64)	匿名用户 ID
     * name	string	匿名用户名称
     * flag	string	匿名用户 flag，在调用禁言 API 时需要传入
     * ```
     */
    interface Anonymous {
        /**
         * 匿名用户 ID
         */
        val id: Long

        /**
         * 匿名用户名称
         */
        val name: String?

        /**
         * 匿名用户 flag，在调用禁言 API 时需要传入
         */
        val flag: String

    }


    /**
     * 发送者信息。
     */
    override val sender: Sender


    /**
     * 发送者信息。
     */
    interface Sender : OneBotMessage.Sender {
        override val userId: Long?
        override val nickname: String?
        /** 群名片／备注 */
        val card: String?
        /** 性别 */
        override val sex: String?
        /** 年龄 */
        override val age: Int?

        /**
         * 地区
         */
        val area: String?

        /**
         * 成员等级
         */
        val level: Int?

        /**
         * 角色，owner 或 admin 或 member
         */
        val role: String?

        /**
         * 专属头衔。
         */
        val title: String?

    }


    /**
     * 群聊事件的子类型对应的约束类型。
     * 群聊事件中可能包含的子类型有：
     * - normal
     * - anonymous
     * - notice
     */
    enum class SubType(override val type: String) : OneBotMessage.MessageEventSubType {
        /** 正常消息 */
        NORMAL("normal"),
        /** 匿名消息 */
        ANONYMOUS("anonymous"),
        /** 系统提示 */
        NOTICE("notice")
    }


}