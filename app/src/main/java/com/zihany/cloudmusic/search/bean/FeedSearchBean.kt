package com.zihany.cloudmusic.search.bean

class FeedSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var videoCount = 0
        var videos: MutableList<VideosBean>? = null

        inner class VideosBean {
            var coverUrl = ""
            var title = ""
            var durationms = 0
            var playTime = 0L
            var type = 0
            var aliaName: Any? = null
            var transName: Any? = null
            var vid = ""
            var alg = ""
            var creator: MutableList<MvBean.CreatorBean>? = null
            var markTypes: MutableList<*>? = null

            override fun toString(): String {
                return "VideosBean(coverUrl='$coverUrl', title='$title', durationms=$durationms, playTime=$playTime, type=$type, aliaName=$aliaName, transName=$transName, vid='$vid', alg='$alg', creator=$creator, markTypes=$markTypes)"
            }

        }
    }
}