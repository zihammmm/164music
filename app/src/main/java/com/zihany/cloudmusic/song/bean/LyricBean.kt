package com.zihany.cloudmusic.song.bean

class LyricBean {
    var sgc = false
    var sfy = false
    var qfy = false
    var transUser: TransUserBean? = null
    var lyricUser: LyricUserBean? = null
    var lrc: LrcBean? = null
    var klyric: KlyricBean? = null
    var tlyric: TlyricBean? = null
    var code = 0

    inner class TransUserBean {
        var id = 0
        var status = 0
        var demand = 0
        var userid = 0
        var nickname: String? = null
        var uptime = 0L
    }

    inner class LyricUserBean {
        var id = 0
        var status = 0
        var demand = 0
        var userid = 0
        var nickname: String? = null
        var uptime = 0L
    }

    inner class LrcBean {
        var version = 0
        var lyric: String? = null
    }

    inner class KlyricBean {
        var version = 0
        var lyric: Any? = null
    }

    inner class TlyricBean {
        var version = 0
        var lyric: String? = null
    }
}