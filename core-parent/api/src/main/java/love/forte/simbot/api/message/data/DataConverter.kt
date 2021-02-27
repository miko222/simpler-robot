/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     DataConverter.kt
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

@file:JvmName("DataConverters")
package love.forte.simbot.api.message.data

import love.forte.simbot.SimbotRuntimeException


/**
 * 数据类类型转化器，针对一个任意类型的实例，将其转化为一个符合 [数据类][DataEntity] 规范的新实例。
 *
 * 被转化的`"任意类型"`应当为一个**接口类型**，返回值应当同时实现此被转化的类型和[数据类][DataEntity]。
 */
public interface DataConverter<T> {

    /**
     * 将一个[T]类型的参数转化为一个[数据类][DataEntity]。如果这个类已经是[DataEntity]的实例，原样返回。
     *
     * 返回的[T]应当实现了[DataEntity]
     *
     * @throws DataEntityException 无法转化或者转化出错时。
     */
    fun convert(target: T): T

}

/**
 * fun for `[DataConverter].invoke()` .
 */
public operator fun <T> DataConverter<T>.invoke(target: T): T = this.convert(target)



/**
 * 基础[数据类类型转化器][DataConverter]。
 */
public abstract class BaseDataConverter<T> : DataConverter<T> {

    override fun convert(target: T): T {
        return if(target is DataEntity) target else doConvert(target)
    }

    /**
     * 进行数据转化，得到[T]的[数据类][DataEntity]实例。[被转化目标][target]不会是[DataEntity]实例。
     *
     * @throws DataEntityException 无法转化或者转化出错时。
     *
     */
    protected abstract fun doConvert(target: T): T


}


/**
 * [数据类][DataEntity]转化管理器，用于记录、注册、使用转化器。
 */
public interface DataConvertManager {

    /**
     * 注册针对某个指定类型的转化器。
     * 同一个类型下，转化器可能会存在多个，此方法为从首部追加一个解析器。
     */
    fun <T> addFirstConvert(targetType: Class<T>, converter: DataConverter<T>)

    /**
     * 注册针对某个指定类型的转化器。
     * 同一个类型下，转化器可能会存在多个，此方法为从尾部追加一个解析器。
     */
    fun <T> addLastConvert(targetType: Class<T>, converter: DataConverter<T>)

    /**
     * 注册针对某个指定类型的转化器。
     * 同一个类型下，转化器可能会存在多个，此方法为直接覆盖重置为当前解析器。
     */
    fun <T> setConvert(targetType: Class<T>, converter: DataConverter<T>)

    /**
     * 移除对某个类型的解析器。
     */
    fun <T> removeConverts(targetType: Class<T>)

    /**
     * 移除对某个类型的解析器的第一个。
     */
    fun <T> removeFirstConvert(targetType: Class<T>)

    /**
     * 移除对某个类型的解析器的最后一个。
     */
    fun <T>  removeLastConvert(targetType: Class<T>)

    /**
     * 判断是否存在对某个类型的解析器。
     */
    fun <T> contains(targetType: Class<T>): Boolean

    /**
     * 得到某个类型对应的所有解析器。
     */
    fun <T> getConverts(targetType: Class<T>): List<DataConverter<T>>

    /**
     * 获取所有已经被注册的解析目标。其中不包括缓存中的子类类型。
     */
    val targets: List<Class<*>>


    /**
     * 执行转化。
     */
    fun <T> convert(target: T): T


    /**
     * 执行转化，并提供一个指定的解析目标。
     */
    fun <T, P> convert(target: T, targetType: Class<T>): T


}










public open class DataEntityException : SimbotRuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace)
}


public open class DataEntityConvertException : DataEntityException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
        message,
        cause,
        enableSuppression,
        writableStackTrace)
}
