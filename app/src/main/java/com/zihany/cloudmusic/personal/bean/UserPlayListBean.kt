package com.zihany.cloudmusic.personal.bean
data class UserPlayListBean(
    val code: Int,
    val more: Boolean,
    val playlist: List<Playlist>,
    val version: String
)

data class Playlist(
    val adType: Int,
    val anonimous: Boolean,
    val artists: Any,
    val backgroundCoverId: Long,
    val backgroundCoverUrl: Any,
    val cloudTrackCount: Int,
    val commentThreadId: String,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val createTime: Long,
    val creator: Creator,
    val description: Any,
    val englishTitle: Any,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val newImported: Boolean,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val playCount: Long,
    val privacy: Int,
    val recommendInfo: Any,
    val specialType: Int,
    val status: Int,
    val subscribed: Boolean,
    val subscribedCount: Int,
    val subscribers: List<Any>,
    val tags: List<Any>,
    val titleImage: Int,
    val titleImageUrl: Any,
    val totalDuration: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: Any,
    val updateFrequency: Any,
    val updateTime: Long,
    val userId: Int
)

data class Creator(
    val accountStatus: Int,
    val authStatus: Int,
    val authority: Int,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarImgId_str: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Long,
    val city: Long,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Int,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Long,
    val remarkName: Any,
    val signature: String,
    val userId: Long,
    val userType: Int,
    val vipType: Int
)
