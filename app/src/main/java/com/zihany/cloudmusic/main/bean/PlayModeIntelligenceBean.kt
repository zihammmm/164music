package com.zihany.cloudmusic.main.bean

class PlayModeIntelligenceBean {
    var code = 0
    var message = ""
    var data: MutableList<DataBean>? = null

    inner class DataBean {
        var id = 0L
        var alg = ""
        var recommended = false
        var songInfo: SongInfoBean? = null

        inner class SongInfoBean {
            var no = 0
            var copyright = 0
            var rt: Any? = null
            var fee = 0
            var rurl: Any? = null
            var privilege: PrivilegeBean? = null
            var mst = 0
            var pst = 0
            var pop = 0
            var dt = 0
            var rtype = 0
            var s_id = 0
            var id = 0L
            var st = 0
            var a: Any? = null
            var cd = ""
            var publishTime = 0L
            var cf = ""
            var h: PrivilegeBean.HBean? = null
            var mv = 0
            var al: PrivilegeBean.AlBean? = null
            var l: PrivilegeBean.LBean? = null
            var cp = 0
            var m: PrivilegeBean.MBean? = null
            var djId = 0
            var crbt: Any? = null
            var rtUrl: Any? = null
            var ftype = 0
            var t = 0
            var v = 0
            var name = ""
            var rtUrls: MutableList<*>? = null
            var alia: MutableList<*>? = null
            var ar: MutableList<PrivilegeBean.ArBean>? = null

            inner class PrivilegeBean {
                var id = 0
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
                var fl = 0L
                var toast = false
                var flag = 0
                var preSell = false

                inner class HBean {
                    var br = 0
                    var fid = 0
                    var size = 0
                    var vd = 0.0
                }

                inner class AlBean {
                    /**
                     * br : 320000
                     * fid : 0
                     * size : 102130460
                     * vd : -4.47
                     */
                    var picUrl = ""
                    var name = ""
                    var pic_str = ""
                    var id = 0
                    var pic = 0L
                    var tns: MutableList<*>? = null
                }

                inner class LBean {
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

                inner class ArBean {
                    var name = ""
                    var id = 0
                    var tns: MutableList<*>? = null
                    var alias: MutableList<*>? = null
                }
            }
        }
    }
}