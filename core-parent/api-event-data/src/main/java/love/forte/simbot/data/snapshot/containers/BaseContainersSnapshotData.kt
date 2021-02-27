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
import love.forte.simbot.api.message.containers.*
import love.forte.simbot.api.message.data.DataEntity
import love.forte.simbot.api.message.results.GroupAdmin
import love.forte.simbot.api.message.results.GroupFullInfo
import love.forte.simbot.api.message.results.GroupOwner
import love.forte.simbot.data.BaseFromData


/**
 * 一个基础的flagContent data。
 */
public data class FlagContentSnapshotData
internal constructor(override val id: String) : FlagContent, DataEntity {
    companion object : BaseFromData<FlagContent, FlagContentSnapshotData>() {
        override fun FlagContent.from(): FlagContentSnapshotData {
            return FlagContentSnapshotData(id)
        }
    }
}


/**
 * [AccountInfo] 的数据快照.
 */
public data class AccountInfoSnapshotData
internal constructor(
    override val accountCode: String,
    override val accountNickname: String?,
    override val accountRemark: String?,
    override val accountAvatar: String?,
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
public data class BotInfoSnapshotData
internal constructor(
    override val botCode: String,
    override val botName: String,
    override val botAvatar: String?,
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
public data class FriendAccountInfoSnapshotData
internal constructor(
    override val accountCode: String,
    override val accountNickname: String?,
    override val accountRemark: String?,
    override val accountAvatar: String?,
) : FriendAccountInfo, DataEntity {
    companion object : BaseFromData<FriendAccountInfo, FriendAccountInfoSnapshotData>() {
        override fun FriendAccountInfo.from(): FriendAccountInfoSnapshotData {
            return FriendAccountInfoSnapshotData(
                accountCode, accountNickname, accountRemark, accountAvatar
            )
        }
    }
}


/**
 * [GroupAccountInfo] 数据快照。
 */
public data class GroupAccountInfoSnapshotData
internal constructor(
    override val accountCode: String,
    override val accountNickname: String?,
    override val accountRemark: String?,
    override val accountAvatar: String?,
    override val accountTitle: String?,
) : GroupAccountInfo, DataEntity {
    companion object : BaseFromData<GroupAccountInfo, GroupAccountInfoSnapshotData>() {
        override fun GroupAccountInfo.from(): GroupAccountInfoSnapshotData {
            return GroupAccountInfoSnapshotData(
                accountCode, accountNickname, accountRemark, accountAvatar, accountTitle
            )
        }
    }
}


/**
 * [SimpleGroupInfo] 的数据快照。
 */
public data class GroupFullInfoSnapshotData
internal constructor(
    override val originalData: String,
    override val groupCode: String,
    override val groupAvatar: String?,
    override val groupName: String?,
    override val maximum: Int,
    override val total: Int,
    override val createTime: Long,
    override val simpleIntroduction: String?,
    override val fullIntroduction: String?,
    override val owner: GroupOwner,
    override val admins: List<GroupAdmin>,
) : GroupFullInfo, DataEntity {
    companion object : BaseFromData<GroupFullInfo, GroupFullInfoSnapshotData>() {
        override fun GroupFullInfo.from(): GroupFullInfoSnapshotData {
            return GroupFullInfoSnapshotData(
                originalData,
                groupCode,
                groupAvatar,
                groupName,
                maximum,
                total,
                createTime,
                simpleIntroduction,
                fullIntroduction,
                owner,
                admins
            )
        }
    }
}


/**
 * [GroupInfo] 的数据快照。
 */
public data class GroupInfoSnapshotData
internal constructor(
    override val groupCode: String,
    override val groupAvatar: String?,
    override val groupName: String?,
) : GroupInfo, DataEntity {
    companion object : BaseFromData<GroupInfo, GroupInfoSnapshotData>() {
        override fun GroupInfo.from(): GroupInfoSnapshotData {
            return GroupInfoSnapshotData(
                groupCode, groupAvatar, groupName
            )
        }
    }
}

/**
 * [OperatorInfo] 的数据快照。
 */
public data class OperatorInfoSnapshotData
internal constructor(
    override val operatorCode: String,
    override val operatorNickname: String?,
    override val operatorRemark: String?,
    override val operatorAvatar: String?,
) : OperatorInfo, DataEntity {
    companion object : BaseFromData<OperatorInfo, OperatorInfoSnapshotData>() {
        override fun OperatorInfo.from(): OperatorInfoSnapshotData {
            return OperatorInfoSnapshotData(operatorCode, operatorNickname, operatorRemark, operatorAvatar)
        }
    }
}


/**
 * [BeOperatorInfo] 的数据快照。
 */
public data class BeOperatorInfoSnapshotData
internal constructor(
    override val beOperatorCode: String,
    override val beOperatorNickname: String?,
    override val beOperatorRemark: String?,
    override val beOperatorAvatar: String?
) : BeOperatorInfo, DataEntity {
    companion object : BaseFromData<BeOperatorInfo, BeOperatorInfoSnapshotData>() {
        override fun BeOperatorInfo.from(): BeOperatorInfoSnapshotData {
            return BeOperatorInfoSnapshotData(
                beOperatorCode, beOperatorNickname, beOperatorRemark, beOperatorAvatar
            )
        }
    }
}










