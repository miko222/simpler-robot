/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     FromData.kt
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

package love.forte.simbot.data

import love.forte.simbot.api.message.data.BaseDataConverter
import love.forte.simbot.api.message.data.DataConverter


public interface FromData<T, D : T> : DataConverter<T> {
    fun from(target: T): D
}

public abstract class BaseFromData<T, D : T> : FromData<T, D>, DataConverter<T>, BaseDataConverter<T>() {
    override fun doConvert(target: T): T = from(target)
}