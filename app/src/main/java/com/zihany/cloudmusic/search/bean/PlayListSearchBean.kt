package com.zihany.cloudmusic.search.bean

class PlayListSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var playlistCount = 0
        var playlistsBean: MutableList<PlaylistsBean>? = null

        inner class PlaylistsBean {
            var id = 0L
            var name = ""
            var coverImgUrl = ""
            var creator: CreatorBean? = null
            var subscribed = false
            var trackCount = 0
            var userId = 0L
            var playCount = 0
            var bookCount = 0
            var description = ""
            var highQuality = false
            var alg = ""

            inner class CreatorBean {
                var nickname = ""
                var userId = 0L
                var userType = 0
                var authStatus = 0
                var expertTags: Any? = null
                var experts: Any? = null
            }
        }
    }
}