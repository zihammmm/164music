package com.zihany.Cloudmusic.main.bean

class DailyRecommendBean {
    var code = 0
    var recommend: MutableList<RecommendBean>? = null

    inner class RecommendBean {
        var name: String? = null
        var id = 0L
        var position = 0
        var status = 0
        var fee = 0
        var copyrightId = 0
        var disc: String? = null
        var no = 0
        var album: AlbumBean? = null
        var starred = false
        var popularity = 0
        var score = 0
        var starredNum = 0
        var duration = 0
        var playedNum = 0
        var dayPlays = 0
        var hearTime = 0
        var ringtone: String? = null
        var crbt: Any? = null
        var audition: Any? = null
        var copyFrom: String? = null
        var commentThreadId: String? = null
        var rtUrl: Any? = null
        var ftype = 0
        var copyright = 0
        var transName: Any? = null
        var sign: Any? = null
        var mark = 0
        var bMusic

        inner class AlbumBean {

        }

        inner class BMusicBean {

        }

        inner class HMusicBean {

        }
    }
}