package com.zihany.cloudmusic.song.bean

class SongDetailBean {
    var code = 0
    var songs: MutableList<SongsBean>? = null
    var privileges: MutableList<SongsBean>? = null

    inner class SongsBean {
        var name: String? = null
        var id = 0L
        var pst = 0
        var t = 0
        var pop = 0
        var st = 0
        var rt: String? = null
        var fee = 0
        var v = 0
        var crbt: Any? = null
        var cf: String? = null
        var al: AlBean? = null
        var dt = 0L
        var h: HBean? = null
        var m: MBean? = null
        var l: LBean? = null
        var a: Any?= null
        var cd: String? = null
        var no = 0
        var rtUrl: Any? = null
        var ftype = 0
        var djId = 0
        var copyright =0
        var sId = 0
        var mark = 0L
        var mv = 0
        var mst = 0
        var cp = 0
        var rtype = 0
        var rurl: Any? = null
        var publishTime: Any? = null
        var ar: MutableList<ArBean>? = null
        var alia: MutableList<*>? = null
        var rtUrls: MutableList<*>? = null

        inner class AlBean {
            var id = 0L
            var name: String? = null
            var picUrl: String? = null
            var picStr: String? = null
            var pic = 0L
            var tns: MutableList<*>? = null
        }

        inner class HBean {
            var br = 0
            var fid = 0
            var size = 0
            var vd = 0.0
        }

        inner class MBean {
            var br = 0
            var fid = 0
            var size = 0
            var vd = 0.0
        }

        inner class LBean {
            var br = 0
            var fid = 0
            var size = 0
            var vd = 0.0
        }

        inner class ArBean {
            var id = 0L
            var name: String? = null
            var tns: MutableList<*>? = null
            var alias: MutableList<*>? = null
        }
    }

    inner class PrivilegesBean {
        var id = 0L
        var fee = 0
        var payed = 0
        var st = 0
        var pl = 0
        var dl = 0
        var sp = 0
        var cp = 0
        var subp = 0
        var cs = false
        var maxbr = 0
        var fl = 0
        var toast = false
        var flag = 0
        var preSell = false
        override fun toString(): String {
            return "PrivilegesBean(id=$id," +
                    " fee=$fee," +
                    " payed=$payed," +
                    " st=$st," +
                    " pl=$pl," +
                    " dl=$dl," +
                    " sp=$sp," +
                    " cp=$cp," +
                    " subp=$subp," +
                    " cs=$cs," +
                    " maxbr=$maxbr," +
                    " fl=$fl," +
                    " toast=$toast," +
                    " flag=$flag," +
                    " preSell=$preSell)"
        }
    }

    override fun toString(): String {
        return "SongDetailBean(code=$code, songs=$songs, privileges=$privileges)"
    }


}