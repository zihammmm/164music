package com.zihany.cloudmusic.search.bean

class SingerSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var artistCount = 0
        var artists: MutableList<ArtistsBean>? = null
        inner class ArtistsBean {
            var id = 0L
            var name = ""
            var picUrl = ""
            var albumSize = 0
            var picId = 0L
            var img1v1Url = ""
            var img1v1 = 0L
            var mvSize =0
            var followed = false
            var alg = ""
            var trans = ""
            var accountId = 0
            var alias: MutableList<String>? = null
            var transNames: MutableList<String>? = null
            var alia: MutableList<String>? = null
        }
    }
}