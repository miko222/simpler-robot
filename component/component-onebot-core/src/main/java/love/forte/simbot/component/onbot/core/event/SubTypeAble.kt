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

package love.forte.simbot.component.onbot.core.event


/**
 * 代表一个事件可能获得 [子类型][subType]。
 */
public interface SubTypeContainer {

    /**
     * 一个事件可能存在的子类型。
     */
    val subType: String

}