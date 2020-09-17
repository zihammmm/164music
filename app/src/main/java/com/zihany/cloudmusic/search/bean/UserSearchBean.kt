package com.zihany.cloudmusic.search.bean

class UserSearchBean {
    var result: ResultBean? = null
    var code = 0

    class ResultBean {
        var userprofileCount = 0
        var userprofiles:MutableList<UserprofilesBean>? = null

        class UserprofilesBean {
            var defaultAvatar = false
            var province = 0
            var authStatus = 0
            var followed = false
            var avatarUrl = ""
            var accountStatus = 0
            var gender = 0
            var city = 0
            var birthday = 0L
            var userId = 0L
            var userType = 0
            var nickname = ""
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
            var alg = ""
            var avatarImgId_str = ""
        }
    }
}