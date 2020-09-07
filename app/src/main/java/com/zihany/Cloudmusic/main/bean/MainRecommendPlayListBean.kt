package com.zihany.Cloudmusic.main.bean

class MainRecommendPlayListBean {
    var code = 0
    var featureFirst = false
    var haveRcmdSongs = false
    var recommend: MutableList<RecommendBean>? = null


    inner class RecommendBean {
        var id = 0L
        var type = 0
        var name: String? = null
        var copywriter: String? = null
        var picUrl: String? = null
        var playcount = 0
        var createTime = 0L
        var creator: CreatorBean? = null
        var trackCount = 0
        var userId = 0
        var alg: String? = null

        inner class CreatorBean {
            var description: String? = null
            var accountStatus = 0
            var userId = 0
            var vipType = 0
            var province = 0
            var avatarUrl: String? = null
            var authStatus = 0
            var userType = 0
            var nickname: String? = null
            var gender = 0
            var birthday = 0L
            var city = 0
            var avatarImgId = 0L
            var backgroundImgId = 0L
            var detailDescription: String? = null
            var defaultAvatar = false
            var expertTags: Any? = null
            var djStatus = 0
            var followed = false
            var backgroundUrl: String? = null
            var avatarImgIdStr: String? = null
            var backgroundImgIdStr: String? = null
            var remarkName: Any? = null
            var mutual = false
            var signature: String? = null
            var authority = 0
        }

        override fun toString(): String {
            return "RecommendBean(id=$id, type=$type, name=$name, copywriter=$copywriter, picUrl=$picUrl, playcount=$playcount, createTime=$createTime, creator=$creator, trackCount=$trackCount, userId=$userId, alg=$alg)"
        }

    }

    override fun toString(): String {
        return "MainRecommendPlayListBean(code=$code, featureFirst=$featureFirst, haveRcmdSongs=$haveRcmdSongs, recommend=$recommend)"
    }


}