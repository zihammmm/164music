package com.zihany.cloudmusic.personal.bean

class UserPlayListBean {
    var more = false
    var code = 0
    var playList: MutableList<PlayListBean>? = null

    inner class PlayListBean {
        var subscribed = false
        var creator: CreatorBean? = null
        var artists: Any? = null
        var tracks: Any? = null
        var updateFrequency: Any? = null
        var backgroundCoverId = 0L
        var backgroundCoverUrl: String? = null
        var privacy = 0
        var newImported = false
        var createTime = 0L
        var highQuality = false
        var userId = 0L
        var coverImgId = 0L
        var updateTime = 0L
        var specialType = 0
        var anonimous = false
        var trackUpdateTime = 0L
        var trackCount = 0
        var commentThreadId: String? = null
        var coverImgUrl: String? = null
        var totalDuration = 0
        var adType = 0
        var trackNumberUpdateTime = 0L
        var description: Any? = null
        var ordered = false
        var status = 0
        var subscribedCount = 0
        var cloudTrackCount = 0
        var playCount = 0L
        var name: String? = null
        var id = 0L
        var coverImgIdStr: String? = null
        var subscribers: MutableList<*>? = null
        var tags: MutableList<*>? = null

        inner class CreatorBean {
            var defaultAvatar = false
            var province = 0L
            var authStatus = 0
            var followed = false
            var avatarUrl = ""
            var accountStatus = 0
            var gender = 0
            var city = 0L
            var birthday = 0L
            var userId = 0L
            var userType = 0
            var nickname= ""
            var signature = ""
            var description = ""
            var detailDescription = ""
            var avatarImgId = 0L
            var backgroundImgId = 0L
            var backgroundUrl = ""
            var authority = 0
            var mutual = false
            var expertTags: Any? = null
            var experts: Any? = null
            var djStatus = 0
            var vipType = 0
            var remarkName: Any? = null
            var avatarImgIdStr = ""
            var backgroundImgIdStr = ""
            var avatarImgId_str = ""
        }
    }
}