package love.forte.simbot.message

import love.forte.simbot.Api4J
import love.forte.simbot.Bot
import love.forte.simbot.ID
import love.forte.simbot.resources.Resource

/**
 * 用于构建 [Messages] 的构建器，提供了针对于 [标准消息][StandardMessage] 的快捷api，
 * 并支持追加其他额外消息。
 *
 * 在Java中，你可以直接实例化并使用此构建器：
 * ```java
 * final MessagesBuilder builder = new MessagesBuilder();
 *
 * final Messages messages = builder.at(Identifies.ID(123))
 *         .face(Identifies.ID("hi"))
 *         .atAll()
 *         .text("Hello ")
 *         .append(Text.of("World"))
 *         .build();
 * ```
 *
 * 在kotlin中，你可以参考使用 [buildMessages] 来得到更佳的使用体验:
 *
 * ```kotlin
 * buildMessages {
 *  + "Hello "
 *  + "World".toText()
 *  + Face(2.ID)
 *  at(567.ID)
 * }
 * ```
 *
 * *此构建器不是线程安全的。*
 *
 * @author ForteScarlet
 *
 * @see buildMessages
 * @see Messages
 */
public class MessagesBuilder
@JvmOverloads constructor(private var messages: Messages = EmptyMessages) {

    private fun appendElement(messageElement: Message.Element<*>): MessagesBuilder = also {
        messages += messageElement
    }

    private fun appendElements(messageElements: Collection<Message.Element<*>>): MessagesBuilder = also {
        messages += messageElements
    }


    //region 整合方法
    /**
     * 拼接一个字符串文本。
     * @see Text
     */
    public fun text(text: String): MessagesBuilder = appendElement(text.toText())

    /**
     * 拼接一个 [at][At]。
     *
     * @see At
     */
    @JvmOverloads
    public fun at(target: ID, atType: String = "user", originContent: String = "@$target"): MessagesBuilder =
        appendElement(At(target, atType, originContent))

    /**
     * 拼接一个 [atAll][AtAll]。
     */
    public fun atAll(): MessagesBuilder = appendElement(AtAll)

    /**
     * 拼接一个[表情][Face]。
     *
     * @see Face
     */
    public fun face(id: ID): MessagesBuilder = appendElement(Face(id))

    /**
     * 拼接一个[emoji][Emoji]。
     *
     * @see Emoji
     */
    public fun emoji(id: ID): MessagesBuilder = appendElement(Emoji(id))

    /**
     * 通过 [Bot.uploadImage] 上传并拼接一个 [Image] 消息到当前消息中。
     *
     * @see Bot.uploadImage
     */
    @JvmSynthetic
    public suspend fun image(bot: Bot, resource: Resource): MessagesBuilder = appendElement(bot.uploadImage(resource))

    /**
     * 通过 [Bot.resolveImage] 获取并拼接一个 [Image] 消息到当前消息中。
     *
     * @see Bot.resolveImage
     */
    @JvmSynthetic
    public suspend fun image(bot: Bot, id: ID): MessagesBuilder = appendElement(bot.resolveImage(id))

    /**
     * 通过 [Bot.uploadImageBlocking] 上传并拼接一个 [Image] 消息到当前消息中。
     *
     * @see Bot.uploadImageBlocking
     */
    @Api4J
    @JvmName("image")
    public fun image4J(bot: Bot, resource: Resource): MessagesBuilder = appendElement(bot.uploadImageBlocking(resource))

    /**
     * 通过 [Bot.resolveImageBlocking] 获取并拼接一个 [Image] 消息到当前消息中。
     *
     * @see Bot.resolveImageBlocking
     */
    @Api4J
    @JvmName("image")
    public fun image4J(bot: Bot, id: ID): MessagesBuilder = appendElement(bot.resolveImageBlocking(id))
    //endregion


    /**
     * 拼接一个字符串文本。
     */
    public fun append(text: String): MessagesBuilder = text(text)

    /**
     * 拼接一个任意消息。
     */
    public fun append(element: Message.Element<*>): MessagesBuilder = appendElement(element)


    /**
     * 拼接多个任意消息。
     */
    public fun append(vararg elements: Message.Element<*>): MessagesBuilder {
        if (elements.isNotEmpty()) {
            appendElements(elements.toList())
        }
        return this
    }

    /**
     * 拼接多个任意消息。
     */
    public fun append(elements: Collection<Message.Element<*>>): MessagesBuilder = appendElements(elements)


    /**
     * 拼接一个字符串文本。
     */
    public operator fun String.unaryPlus(): MessagesBuilder = append(this)

    /**
     * 拼接一个任意消息。
     */
    public operator fun Message.Element<*>.unaryPlus(): MessagesBuilder = appendElement(this)


    /**
     * 拼接多个任意消息。
     */
    public operator fun Collection<Message.Element<*>>.unaryPlus(): MessagesBuilder = appendElements(this)


    /**
     * 获取当前构建器中的 [Messages] 实例。
     */
    public fun build(): Messages = messages
}


/**
 * 通过 [MessagesBuilder] 构建 [Messages].
 *
 * ```kotlin
 * buildMessages {
 *  + "Hello "
 *  + "World".toText()
 *  + Face(2.ID)
 *  at(567.ID)
 * }
 * ```
 *
 */
public inline fun buildMessages(block: MessagesBuilder.() -> Unit): Messages {
    return MessagesBuilder().also(block).build()
}



