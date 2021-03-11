/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     OneBotMessage.kt
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

@file:JvmName("OneBotMessages")

package love.forte.simbot.component.onbot.core.message


/**
 * onebot [消息][https://github.com/howmanybots/onebot/tree/master/v12-draft/specs/message] 类型接口。
 *
 *
 * 此接口为定义一个onebot中的 [消息段](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/segment.md)
 *
 *
 * 格式参考 [消息段格式](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/array.md#%E6%B6%88%E6%81%AF%E6%AE%B5)
 */
public interface OneBotMessageSegment<D: SegmentData> {

    /**
     * 消息段类型
     */
    val type: String

    /**
     * 获取对应的属性。
     */
    val data: D
}

/**
 * 消息段中对应的 `data` 属性值。
 */
public interface SegmentData {

    /**
     * 通过一个指定的属性名获取对应的属性。
     */
    fun getValue(key: String): String?

    /**
     * 空实例。
     */
    companion object Empty : SegmentData {
        override fun getValue(key: String): String? = null
    }

}


public operator fun <D: SegmentData> OneBotMessageSegment<D>.get(key: String): String? = this.data?.getValue(key)
public operator fun SegmentData?.get(key: String): String? = this?.getValue(key)


/**
 * 通过 [数据映射表][Map] 得到一个 [SegmentData] 实例。
 */
public fun segmentData(dataMap: Map<String, String>): SegmentData {
    return MapSegmentData(dataMap.toMap())
}


private class MapSegmentData(private val delegate: Map<String, String>) : SegmentData {
    /**
     * 通过一个指定的属性名获取对应的属性。
     */
    override fun getValue(key: String): String? = delegate[key]
}



/**
 * 数组格式的onebot消息格式。
 *
 *
 * see: [数组格式](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/array.md)
 */
public interface OneBotMessageSegmentArray : List<OneBotMessageSegment<*>>


/**
 * 通过 [segments] 得到一个 [OneBotMessageSegmentArray] 实例。
 */
public fun segmentArray(segments: Collection<OneBotMessageSegment<*>>): OneBotMessageSegmentArray {
    return when {
        segments.isEmpty() -> EmptyOneBotMessageSegmentArray
        segments is List -> OneBotMessageSegmentArrayDelegateList(segments)
        else -> OneBotMessageSegmentArrayDelegateList(segments.toList())
    }
}


public fun segmentArray(vararg segments: OneBotMessageSegment<*>): OneBotMessageSegmentArray {
    return when {
        segments.isEmpty() -> EmptyOneBotMessageSegmentArray
        else -> OneBotMessageSegmentArrayDelegateList(segments.asList())
    }
}


public operator fun OneBotMessageSegmentArray.plus(other: OneBotMessageSegmentArray): OneBotMessageSegmentArray {
    if (this == EmptyOneBotMessageSegmentArray) {
        return other
    }

    if (other == EmptyOneBotMessageSegmentArray) {
        return this
    }

    if (this is OneBotMessageSegmentArrayDelegateList && other is OneBotMessageSegmentArrayDelegateList) {
        return segmentArray(this.delegate + other.delegate)
    }

    val thisList = if (this is OneBotMessageSegmentArrayDelegateList) this.delegate else this
    val otherList = if (other is OneBotMessageSegmentArrayDelegateList) other.delegate else other

    val finalList: MutableList<OneBotMessageSegment<*>> = mutableListOf<OneBotMessageSegment<*>>().apply {
        addAll(thisList)
        addAll(otherList)
    }

    return segmentArray(finalList)
}



private class OneBotMessageSegmentArrayDelegateList(internal val delegate: List<OneBotMessageSegment<*>>) :
    OneBotMessageSegmentArray, List<OneBotMessageSegment<*>> by delegate


private object EmptyOneBotMessageSegmentArray :
    OneBotMessageSegmentArray, List<OneBotMessageSegment<*>> by emptyList()
