package com.zihany.cloudmusic.search.bean

class RadioSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var djRadiosCount = 0
        var djRadios: MutableList<DjRadiosBean>? = null

        inner class DjRadiosBean {
            var id = 0L
            var dj: DjBean? = null
            var name = ""
            var picUrl = ""
            var desc = ""
            var subCount = 0
            var programCount = 0
            var createTime = 0L
            var categoryId = 0
            var category = ""
            var radioFeeType = 0
            var feeScope = 0
            var buyed = false
            var videos: Any? = null
            var finished = false
            var underShelf = false
            var purchaseCount = 0
            var price = 0
            var originalPrice = 0
            var discountPrice: Any? = null
            var lastProgramCreateTime = 0L
            var lastProgramName = ""
            var lastPorgramId = 0L
            var picId = 0L
            var rcmdText: Any? = null
            var composeVideo = false
            var shareCount = 0
            var rcmdtext: Any? = null
            var likedCount = 0
            var alg = ""
            var commentCount = 0

            inner class DjBean {
                var defaultAvatar = false
                var province = 0
                var authStatus = 0
                var followed = false
                var avatarUrl = ""
                var accountStatus = 0
                var gender = 0
                var city = 0
                var birthday = 0L
                var userId = 0
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
                var backgroundImgIdStr = ""
                var avatarImgIdStr = ""
                var avatarImgId_str = ""
            }
        }
    }
}