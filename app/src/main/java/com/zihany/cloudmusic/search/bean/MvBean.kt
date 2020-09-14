package com.zihany.cloudmusic.search.bean

class MvBean {
    var coverUrl = ""
    var title = ""
    var duration = 0L
    var type = 0
    var creator: MutableList<CreatorBean>? = null
    var vid = ""
    var playTime = 0L

    inner class CreatorBean {
        var userId = 0L
        var userName = ""
    }

    override fun toString(): String {
        return "MvBean(coverUrl='$coverUrl', title='$title', duration=$duration, type=$type, creator=$creator, vid='$vid', playTime=$playTime)"
    }

}