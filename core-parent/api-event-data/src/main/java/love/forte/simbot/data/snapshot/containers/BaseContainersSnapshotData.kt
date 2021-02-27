/*
 *
 *  * Copyright (c) 2020. ForteScarlet All rights reserved.
 *  * Project  component-onebot
 *  * File     BaseContainersSnapshotData.kt
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

package love.forte.simbot.data.snapshot.containers

import love.forte.simbot.api.message.assists.FlagContent
import love.forte.simbot.api.message.containers.AccountInfo
import love.forte.simbot.api.message.containers.BotInfo
import love.forte.simbot.api.message.containers.FriendAccountInfo
import love.forte.simbot.api.message.data.DataEntity
import love.forte.simbot.data.BaseFromData


/**
 * 一个基础的flagContent data。
 */
public data class FlagContentSnapshotData(override val id: String) : FlagContent, DataEntity {
    companion object : BaseFromData<FlagContent, FlagContentSnapshotData>() {
        override fun FlagContent.from(): FlagContentSnapshotData {
            return FlagContentSnapshotData(id)
        }
    }
}


/**
 * [AccountInfo] 的数据快照.
 */
public data class AccountInfoSnapshotData(
    override val accountCode: String,
    override val accountNickname: String?,
    override val accountRemark: String?,
    override val accountAvatar: String?
) : AccountInfo, DataEntity {
    companion object : BaseFromData<AccountInfo, AccountInfoSnapshotData>() {
        override fun AccountInfo.from(): AccountInfoSnapshotData {
            return AccountInfoSnapshotData(
                accountCode, accountNickname, accountRemark, accountAvatar
            )
        }
    }
}


/**
 * [BotInfo] 的数据快照。
 */
public data class BotInfoSnapshotData(
    override val botCode: String,
    override val botName: String,
    override val botAvatar: String?
) : BotInfo, DataEntity {
    companion object : BaseFromData<BotInfo, BotInfoSnapshotData>() {
        override fun BotInfo.from(): BotInfoSnapshotData {
            return BotInfoSnapshotData(
                botCode, botName, botAvatar
            )
        }
    }
}

/**
 * [FriendAccountInfo] 的数据快照。
 */
public data class FriendAccountInfoSnapshotData(
    override val accountCode: String,
    override val accountNickname: String?,
    override val accountRemark: String?,
    override val accountAvatar: String?
): FriendAccountInfo, DataEntity {
    companion object : BaseFromData<FriendAccountInfo, FriendAccountInfoSnapshotData>() {
        override fun FriendAccountInfo.from(): FriendAccountInfoSnapshotData {
            return FriendAccountInfoSnapshotData(
                accountCode, accountNickname, accountRemark, accountAvatar
            )
        }
    }
}


