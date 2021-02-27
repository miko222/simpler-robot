/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     BaseMsgGetData.kt
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

@file:JvmName("BaseMsgGetSnapshotData")

package love.forte.simbot.data.snapshot.events

import love.forte.simbot.api.message.containers.AccountInfo
import love.forte.simbot.api.message.containers.BotInfo
import love.forte.simbot.api.message.data.EventDataEntity
import love.forte.simbot.api.message.events.MsgGet
import love.forte.simbot.data.BaseFromData
import love.forte.simbot.data.snapshot.containers.AccountInfoSnapshotData.Companion.from
import love.forte.simbot.data.snapshot.containers.BotInfoSnapshotData.Companion.from


public data class MsgGetData
internal constructor(
    override val accountInfo: AccountInfo,
    override val botInfo: BotInfo,
    override val originalData: String,
    override val id: String,
    override val text: String?,
    override val time: Long,
) : MsgGet, EventDataEntity {
    companion object : BaseFromData<MsgGet, MsgGetData>() {
        override fun MsgGet.from(): MsgGetData {
            return MsgGetData(
                accountInfo.from(),
                botInfo.from(),
                originalData,
                id,
                text,
                time
            )
        }
    }
}



