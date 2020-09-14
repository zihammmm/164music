package com.zihany.cloudmusic.search.bean

class HotSearchDetailBean {
    var code = 0
    var message = ""
    var data: MutableList<DataBean>? = null

    inner class DataBean {
        var searchWord = ""
        var score = 0L
        var content = ""
        var source = 0
        var iconType = 0
        var iconUrl = ""
        var url = ""
        var alg = ""
    }
}