package com.zihany.cloudmusic.main.bean
/**
 * 歌单详情的bean，里面包含了歌单的歌曲
 */

data class PlaylistDetailBean(
    val code: Int,
    val playlist: Playlist,
    val privileges: List<Privilege>,
    val relatedVideos: Any,
    val urls: Any
)

data class Playlist(
        val adType: Int,
        val backgroundCoverId: Long,
        val backgroundCoverUrl: String,
        val cloudTrackCount: Int,
        val commentCount: Int,
        val commentThreadId: String,
        val coverImgId: Long,
        val coverImgId_str: String,
        val coverImgUrl: String,
        val createTime: Long,
        val creator: CreatorBean,
        val description: String,
        val englishTitle: String,
        val highQuality: Boolean,
        val id: Long,
        val name: String,
        val newImported: Boolean,
        val opRecommend: Boolean,
        val ordered: Boolean,
        val playCount: Int,
        val privacy: Int,
        val shareCount: Int,
        val specialType: Int,
        val status: Int,
        val subscribed: Boolean,
        val subscribedCount: Int,
        val subscribers: List<Subscriber>,
        val tags: List<String>,
        val titleImage: Long,
        val titleImageUrl: String,
        val trackCount: Int,
        val trackIds: List<TrackId>,
        val trackNumberUpdateTime: Long,
        val trackUpdateTime: Long,
        val tracks: List<Track>,
        val updateFrequency: String,
        val updateTime: Long,
        val userId: Int,
        val videoIds: Any,
        val videos: Any
)

data class Privilege(
    val chargeInfoList: List<ChargeInfo>,
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val downloadMaxbr: Int,
    val fee: Int,
    val fl: Int,
    val flag: Int,
    val id: Int,
    val maxbr: Int,
    val payed: Int,
    val pl: Int,
    val playMaxbr: Int,
    val preSell: Boolean,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
)

data class CreatorBean(
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
    val city: Int,
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
    val province: Int,
    val remarkName: Any,
    val signature: String,
    val userId: Int,
    val userType: Int,
    val vipType: Int
)

data class Subscriber(
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
    val city: Int,
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
    val province: Int,
    val remarkName: Any,
    val signature: String,
    val userId: Long,
    val userType: Int,
    val vipType: Int
)

data class TrackId(
    val alg: String,
    val at: Long,
    val id: Int,
    val v: Int
)

data class Track(
    val a: Any,
    val al: Al,
    val alg: String,
    val alia: List<String>,
    val ar: List<Ar>,
    val cd: String,
    val cf: String,
    val copyright: Int,
    val cp: Int,
    val crbt: Any,
    val djId: Int,
    val dt: Long,
    val fee: Int,
    val ftype: Int,
    val h: H,
    val id: Int,
    val l: L,
    val m: M,
    val mark: Long,
    val mst: Int,
    val mv: Int,
    val name: String,
    val no: Int,
    val noCopyrightRcmd: Any,
    val originCoverType: Int,
    val pop: Int,
    val pst: Int,
    val publishTime: Long,
    val rt: String,
    val rtUrl: Any,
    val rtUrls: List<Any>,
    val rtype: Int,
    val rurl: Any,
    val s_id: Int,
    val st: Int,
    val t: Int,
    val v: Int
)

data class Al(
    val id: Int,
    val name: String,
    val pic: Long,
    val picUrl: String,
    val pic_str: String,
    val tns: List<Any>
)

data class Ar(
    val alias: List<Any>,
    val id: Int,
    val name: String,
    val tns: List<Any>
)

data class H(
    val br: Int,
    val fid: Int,
    val size: Int,
    val vd: Int
)

data class L(
    val br: Int,
    val fid: Int,
    val size: Int,
    val vd: Int
)

data class M(
    val br: Int,
    val fid: Int,
    val size: Int,
    val vd: Int
)

data class ChargeInfo(
    val chargeMessage: Any,
    val chargeType: Int,
    val chargeUrl: Any,
    val rate: Int
)