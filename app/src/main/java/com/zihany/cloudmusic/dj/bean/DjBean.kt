package com.zihany.cloudmusic.dj.bean

class DjBean {
    var rid: Long = 0
    var coverUrl: String? = null
    var djName: String? = null
    var rcmdName: String? = null
    var programCount = 0
    var registerCount = 0
    var artistName: String? = null
    var price = 0
    var subed = false

    override fun toString(): String {
        return "DjBean{" +
                "rid=" + rid +
                ", coverUrl='" + coverUrl + '\'' +
                ", djName='" + djName + '\'' +
                ", rcmdName='" + rcmdName + '\'' +
                ", programCount=" + programCount +
                ", registerCount=" + registerCount +
                ", artistName='" + artistName + '\'' +
                ", price=" + price +
                ", subed=" + subed +
                '}'
    }
}