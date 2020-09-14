package com.zihany.cloudmusic.search.bean

class SongSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var songCount = 0
        var songs: MutableList<SongsBean>? = null

        inner class SongsBean {
            var id = 0L
            var name = ""
            var album: AlbumBean? = null
            var duration = 0
            var copyrightId = 0
            var status = 0
            var rtype = 0
            var ftype = 0
            var mvid = 0
            var fee = 0
            var rUrl: Any? = null
            var mark = 0L
            var artists: MutableList<ArtistsBean>? = null
            var alias: MutableList<*>? = null

            inner class AlbumBean {
                var id = 0
                var name = ""
                var artist: ArtistBean? = null
                var publishTime = 0L
                var size = 0
                var copyrightId = 0
                var status = 0
                var picId = 0L
                var mark = 0L

                inner class ArtistBean {
                    var id = 0
                    var name = ""
                    var picUrl: Any? = null
                    var albumSize = 0
                    var picId = 0
                    var img1v1Url = ""
                    var img1v1 = 0
                    var trans: Any? = null
                    var alias: MutableList<*>? = null
                }
            }

            inner class ArtistsBean {
                var id = 0
                var name = ""
                var picUrl: Any? = null
                var albumSize = 0
                var picId = 0
                var img1v1Url = ""
                var img1v1 = 0
                var trans: Any? = null
                var alias: MutableList<*>? = null
            }

            override fun toString(): String {
                return "SongsBean(id=$id, name='$name', album=$album, duration=$duration, copyrightId=$copyrightId, status=$status, rtype=$rtype, ftype=$ftype, mvid=$mvid, fee=$fee, rUrl=$rUrl, mark=$mark, artists=$artists, alias=$alias)"
            }

        }

        override fun toString(): String {
            return "ResultBean(songCount=$songCount, songs=$songs)"
        }

    }

    override fun toString(): String {
        return "SongSearchBean(result=$result, code=$code)"
    }

}