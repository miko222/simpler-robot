/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     SubTypeAble.kt
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

@file:JvmName("SubTypes")
package love.forte.simbot.component.onbot.core.event


/**
 * 代表一个事件可能获得 [子类型][subType]。
 */
public interface SubTypeContainer<S : SubType> {

    /**
     * 子类型约束值，即一个 [SubType] 接口的实现类，一般由枚举类实现，以达到范围约束的效果。
     */
    val subTypeConstraint: S

}


/**
 * 一个事件可能存在的子类型字符串, 即[类型约束][SubTypeContainer.subTypeConstraint] 中对应区间约束元素的[类型值][SubType.type]。
 */
public val SubTypeContainer<*>.subType: String
    get() = subTypeConstraint.type


/**
 * 一个 [subType 子类型][SubTypeContainer.subType] 所对应的具体类型值。
 *
 *
 * 在 [SubTypeContainer] 中，[SubTypeContainer.subType] 是 [字符串][String] 类型的，
 * 但是不出意外的话，子类型总是会存在一定的区间范围，例如私聊消息中，可能值为 `friend`、`group`、`other`。
 *
 *
 * 此接口提供一种约束，用于给一个可限定范围的类进行实现，例如一个 [枚举类][Enum].
 *
 */
public interface SubType {

    /**
     * 子类型对应的 `subType` 字符串.
     */
    val type: String
}
