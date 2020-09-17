package com.zihany.cloudmusic.dj.bean

class DjRecommandBean {
    var name: String? = null
    var code: Int? = null
    var djRadios: MutableList<DjRadiosBean>? = null

    class DjRadiosBean {
        var id: Long = 0
        var name: String? = null
        var picUrl: String? = null
        var programCount = 0
        var subCount = 0
        var createTime: Long = 0
        var categoryId = 0
        var category: String? = null
        var rcmdtext: String? = null
        var radioFeeType = 0
        var feeScope = 0
        var subed = false
        var dj: DjBean? = null
        var copywriter: String? = null
        var buyed = false
        var originalPrice = 0

        class DjBean {
            var defaultAvatar = false
            var province = 0
            var authStatus = 0
            var followed = false
            var avatarUrl: String? = null
            var accountStatus = 0
            var gender = 0
            var city = 0
            var birthday: Long = 0
            var userId = 0
            var userType = 0
            var nickname: String? = null
            var signature: String? = null
            var description: String? = null
            var detailDescription: String? = null
            var avatarImgId: Long = 0
            var backgroundImgId: Long = 0
            var backgroundUrl: String? = null
            var authority = 0
            var mutual = false
            var expertTags: Any? = null
            var experts: Any? = null
            var djStatus = 0
            var vipType = 0
            var remarkName: Any? = null
            var avatarImgIdStr: String? = null
            var backgroundImgIdStr: String? = null
        }

        override fun toString(): String {
            return "DjRadioBean(id=$id, name=$name, picUrl=$picUrl, programCount=$programCount, subCount=$subCount, createTime=$createTime, categoryId=$categoryId, category=$category, rcmdtext=$rcmdtext, radioFeeType=$radioFeeType, feeScope=$feeScope, subed=$subed, dj=$dj, copywriter=$copywriter, buyed=$buyed, originalPrice=$originalPrice)"
        }
    }
}