/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     DataAble.kt
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

package love.forte.simbot.api.message.data


/**
 *
 * 标记一个[事件消息][love.forte.simbot.api.message.events]或[响应信息][love.forte.simbot.api.message.results]
 * 为一个标准数据类。
 *
 * ### 数据类
 * 即仅存在字段、getter/\[setter]、toString、hashCode、equals实现的类。
 * 如果数据类中存在其他实例引用，应当也是数据类。
 *
 * 此接口目前仅用于标记。
 *
 *
 *
 */
public interface DataEntity

/**
 * 一个[事件消息][love.forte.simbot.api.message.events]的[数据类][DataEntity]标记接口。
 */
public interface EventDataEntity : DataEntity


/**
 * 一个[事件消息][love.forte.simbot.api.message.events]的[数据类][DataEntity]标记接口。
 */
public interface ResultDataEntity : DataEntity
