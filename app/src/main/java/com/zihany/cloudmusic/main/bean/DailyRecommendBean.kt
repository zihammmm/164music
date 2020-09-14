package com.zihany.cloudmusic.main.bean

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
        var bMusic: BMusicBean? = null
        var mp3Url: Any? = null
        var rtype = 0
        var rurl: Any? = null
        var mvid = 0
        var hMusic: HMusicBean? = null
        var mMusic: MMusicBean? = null
        var lMusic: LMusicBean? = null
        var reason: String? = null
        var privilege: PrivilegeBean? = null
        var alg: String? = null
        var alias: MutableList<*>? = null
        var artists: MutableList<ArtistsBeanX>? = null
        var rtUrls: MutableList<*>? = null
        var transNames: MutableList<String>? = null

        inner class AlbumBean {
            var name: String? = null
            var id = 0L
            var type: String? = null
            var size = 0
            var picId = 0L
            var blurPicUrl: String? = null
            var companyId = 0
            var pic = 0L
            var picUrl: String? = null
            var publishTime = 0L
            var description: String? = null
            var tags: String? = null
            var company: String? = null
            var briefDesc: String? = null
            var artist: ArtistBean? = null
            var status = 0
            var copyrightId = 0
            var commentThreadId: String? = null
            var subType: String? = null
            var transName: Any? = null
            var mark = 0
            var songs: MutableList<*>? = null
            var alias: MutableList<*>? = null
            var artists: MutableList<ArtistsBean>? = null

            inner class ArtistBean {
                var name: String? = null
                var id = 0L
                var picId = 0
                var img1v1Id = 0
                var briefDesc: String? = null
                var picUrl: String? = null
                var img1v1Url: String? = null
                var albumSize = 0
                var trans: String? = null
                var musicSize = 0
                var topicPerson = 0
                var alias: MutableList<*>? = null
            }

            inner class ArtistsBean {
                var name: String? = null
                var id = 0L
                var picId = 0
                var img1v1Id = 0
                var briefDesc: String? = null
                var picUrl: String? = null
                var img1v1Url: String? = null
                var albumSize = 0
                var trans: String? = null
                var musicSize = 0
                var topicPerson = 0
                var alias: MutableList<*>? = null
            }
        }

        inner class BMusicBean {
            var name: Any? = null
            var id = 0L
            var size = 0
            var extension: String? = null
            var sr = 0
            var dfsId = 0
            var bitrate = 0
            var playTime = 0
            var volumeDelta = 0.0
        }

        inner class HMusicBean {
            var name: Any? = null
            var id = 0L
            var size = 0
            var extension: String? = null
            var sr = 0
            var dfsId = 0
            var bitrate = 0
            var playTime = 0
            var volumeDelta = 0.0
        }

        inner class MMusicBean {
            var name: Any? = null
            var id = 0L
            var size = 0
            var extension: String? = null
            var sr = 0
            var dfsId = 0
            var bitrate = 0
            var playTime = 0
            var volumeDelta = 0.0
        }

        inner class LMusicBean {
            var name: Any? = null
            var id = 0L
            var size = 0
            var extension: String? = null
            var sr = 0
            var dfsId = 0
            var bitrate = 0
            var playTime = 0
            var volumeDelta = 0.0
        }

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
            var fl = 0
            var toast = false
            var flag = 0
            var preSell = false
        }

        inner class ArtistsBeanX {
            var name: String? = null
            var id = 0L
            var picId = 0
            var img1v1Id = 0
            var briefDesc: String? = null
            var picUrl: String? = null
            var img1v1Url: String? = null
            var albumSize = 0
            var trans: String? = null
            var musicSize = 0
            var topicPerson = 0
            var alias: MutableList<*>? = null
        }

        override fun toString(): String {
            return "RecommendBean(name=$name, id=$id, position=$position, status=$status, fee=$fee, copyrightId=$copyrightId, disc=$disc, no=$no, album=$album, starred=$starred, popularity=$popularity, score=$score, starredNum=$starredNum, duration=$duration, playedNum=$playedNum, dayPlays=$dayPlays, hearTime=$hearTime, ringtone=$ringtone, crbt=$crbt, audition=$audition, copyFrom=$copyFrom, commentThreadId=$commentThreadId, rtUrl=$rtUrl, ftype=$ftype, copyright=$copyright, transName=$transName, sign=$sign, mark=$mark, bMusic=$bMusic, mp3Url=$mp3Url, rtype=$rtype, rurl=$rurl, mvid=$mvid, hMusic=$hMusic, mMusic=$mMusic, lMusic=$lMusic, reason=$reason, privilege=$privilege, alg=$alg, alias=$alias, artists=$artists, rtUrls=$rtUrls, transNames=$transNames)"
        }
    }

    override fun toString(): String {
        return "DailyRecommendBean(code=$code, recommend=$recommend)"
    }

}