package com.zihany.cloudmusic.search.bean

class AlbumSearchBean {
    var result: ResultBean? = null
    var code = 0

    inner class ResultBean {
        var albumCount = 0
        var albums: MutableList<AlbumsBean>? = null

        inner class AlbumsBean {
            var name = ""
            var id = 0L
            var type = ""
            var size = 0
            var picId = 0L
            var blurPicUrl = ""
            var companyId = 0
            var pic = 0L
            var picUrl = ""
            var publishTime = 0L
            var description = ""
            var tags = ""
            var company = ""
            var briefDesc = ""
            var artist: ArtistBean? = null
            var songs: Any? = null
            var status = 0
            var copyrightId = 0
            var commentThreadId = ""
            var paid = false
            var onSale = false
            var alg = ""
            var mark = 0
            var containedSong = ""
            var alias: MutableList<*>? = null
            var artists: MutableList<ArtistsBean>?= null

            inner class ArtistBean {
                var name = ""
                var id = 0
                var picId = 0L
                var img1v1Id = 0L
                var briefDesc = ""
                var picUrl = ""
                var img1v1Url = ""
                var albumSize = 0
                var trans = ""
                var musicSize = 0
                var topicPerson = 0
                var img1v1Id_Str = ""
                var alias: MutableList<*>? = null
                var transNames: MutableList<String>? = null
                var alia: MutableList<*>? = null
            }

            inner class ArtistsBean {
                var name = ""
                var id = 0
                var picId = 0L
                var img1v1Id = 0L
                var briefDesc = ""
                var picUrl = ""
                var img1v1Url = ""
                var albumSize = 0
                var trans = ""
                var musicSize = 0
                var topicPerson = 0
                var img1v1Id_Str = ""
                var alias: MutableList<*>? = null
            }
        }
    }
}