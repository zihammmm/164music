package com.zihany.cloudmusic.main.bean

class ArtistSublistBean {
    var hasMore = false
    var count = 0
    var code = 0
    var data: MutableList<DataBean>? = null

    inner class DataBean {
        var info = ""
        var id = 0L
        var name = ""
        var trans = ""
        var albumSize = 0
        var mvSize = 0
        var picId = 0L
        var picUrl = ""
        var img1v1Url = ""
        var alias: MutableList<String>? = null
    }
}