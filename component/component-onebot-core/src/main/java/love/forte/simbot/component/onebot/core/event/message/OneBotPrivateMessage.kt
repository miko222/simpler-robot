/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     PrivateMsgEvent.kt
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
 * [私聊消息事件](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/event/message.md#%E7%A7%81%E8%81%8A%E6%B6%88%E6%81%AF).
 *
 *
 * 相关参数:
 *
 * ```
 * 事件数据
 * 字段名	数据类型	可能的值	说明
 * time	number (int64)	-	事件发生的时间戳
 * self_id	number (int64)	-	收到事件的机器人 QQ 号
 * post_type	string	message	上报类型
 * message_type	string	private	消息类型
 * sub_type	string	friend、group、other	消息子类型，如果是好友则是 friend，如果是群临时会话则是 group
 * message_id	number (int32)	-	消息 ID
 * user_id	number (int64)	-	发送者 QQ 号
 * message	message	-	消息内容
 * raw_message	string	-	原始消息内容
 * font	number (int32)	-	字体
 * sender	object	-	发送人信息
 *
 * 其中 sender 字段的内容如下：
 *
 * 字段名	数据类型	说明
 * user_id	number (int64)	发送者 QQ 号
 * nickname	string	昵称
 * sex	string	性别，male 或 female 或 unknown
 * age	number (int32)	年龄
 *
 * 需要注意的是，sender 中的各字段是尽最大努力提供的，也就是说，不保证每个字段都一定存在，也不保证存在的字段都是完全正确的（缓存可能过期）。
 *
 * 快速操作
 * 字段名	数据类型	说明	默认情况
 * reply	message	要回复的内容	不回复
 * auto_escape	boolean	消息内容是否作为纯文本发送（即不解析 CQ 码），只在 reply 字段是字符串时有效	不转义
 *
 * ```
 *
 *
 */
public interface OneBotPrivateMessage : OneBotMessage<OneBotPrivateMessage.SubType> {

    /**
     * 私聊消息，消息类型。可能值：`private`
     */
    override val messageType: String
        get() = "private"


    /**
     * 私聊消息的[子类型约束][SubType]。
     */
    override val subTypeConstraint: SubType


    /**
     * 消息ID
     */
    override val messageId: String


    /**
     * 消息正文
     */
    override val message: OneBotMessageSegment<*>


    /**
     * 用户(发送者)对应账号
     */
    override val userId: Long


    /**
     * 事件消息的原始消息段。
     */
    override val rawMessage: String


    /**
     * 字体对应ID
     */
    val font: Int


    /**
     * 发送人信息.
     */
    override val sender: Sender


    /**
     * 发送人信息.
     */
    interface Sender : OneBotMessage.Sender {

        /** 发送者 QQ 号 */
        override val userId: Long?

        /** 昵称 */
        override val nickname: String?

        /** 性别, male 或 female 或 unknown */
        override val sex: String?

        /** 年龄 */
        override val age: Int?
    }



    /**
     * 私聊消息对应子类型约束枚举。
     *
     * 私聊类型子类型对应可能的值：
     * - `friend`
     * - `group`
     * - `other`
     *
     */
    enum class SubType(override val type: String) : OneBotMessage.MessageEventSubType {
        /** 好友私聊 */
        FRIEND("friend"),

        /** 群临时会话 */
        GROUP("group"),

        /** 其他可能 */
        OTHER("other")
    }

}