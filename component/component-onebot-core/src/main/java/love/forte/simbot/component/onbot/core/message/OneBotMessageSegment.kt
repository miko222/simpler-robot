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
public interface OneBotMessageSegment {

    /**
     * 消息段类型
     */
    val type: String

    /**
     * 获取一个参数值，如果没有则为null。
     */
    fun getData(key: String): String?


    /**
     * 获取data中所有的key。
     */
    val dataKeys: Set<String>


    /**
     * 获取所有参数映射表。
     */
    val data: Map<String, String>
}


/**
 * 数组格式的onebot消息格式。
 *
 *
 * see: [数组格式](https://github.com/howmanybots/onebot/blob/master/v12-draft/specs/message/array.md)
 */
public interface OneBotMessageSegmentArray : List<OneBotMessageSegment>


/**
 * 通过 [segments] 得到一个 [OneBotMessageSegmentArray] 实例。
 */
public fun segmentArray(segments: Collection<OneBotMessageSegment>): OneBotMessageSegmentArray {
    return when {
        segments.isEmpty() -> EmptyOneBotMessageSegmentArray
        segments is List -> OneBotMessageSegmentArrayDelegateList(segments)
        else -> OneBotMessageSegmentArrayDelegateList(segments.toList())
    }
}


public fun segmentArray(vararg segments: OneBotMessageSegment): OneBotMessageSegmentArray {
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

    val finalList: MutableList<OneBotMessageSegment> = mutableListOf<OneBotMessageSegment>().apply {
        addAll(thisList)
        addAll(otherList)
    }

    return segmentArray(finalList)
}



private class OneBotMessageSegmentArrayDelegateList(internal val delegate: List<OneBotMessageSegment>) :
    OneBotMessageSegmentArray, List<OneBotMessageSegment> by delegate


private object EmptyOneBotMessageSegmentArray :
    OneBotMessageSegmentArray, List<OneBotMessageSegment> by emptyList()
