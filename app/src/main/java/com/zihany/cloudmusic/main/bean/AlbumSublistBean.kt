package com.zihany.cloudmusic.main.bean

class AlbumSublistBean {
    var count = 0
    var hasMore = false
    var paidCount = 0
    var code = 0
    var data: MutableList<DataBean>? = null

    inner class DataBean {
        var subTime = 0L
        var picId = 0L
        var picUrl = ""
        var name = ""
        var id = 0L
        var size = 0
        var msg: MutableList<*>? = null
        var alias: MutableList<String>? = null
        var artists: MutableList<ArtistsBean>? = null
        var transNames: MutableList<*>? = null

        inner class ArtistsBean {
            var img1v1Id = 0L
            var topicPerson = 0
            var picId = 0L
            var albumSize = 0
            var musicSize = 0
            var briefDesc = ""
            var followed = false
            var img1v1Url = ""
            var trans = ""
            var picUrl = ""
            var name = ""
            var id = 0L
            var img1v1Id_str = ""
            var alias: MutableList<*>? = null

            override fun toString(): String {
                return "ArtistsBean(img1v1Id=$img1v1Id, topicPerson=$topicPerson, picId=$picId, albumSize=$albumSize, musicSize=$musicSize, briefDesc='$briefDesc', followed=$followed, img1v1Url='$img1v1Url', trans='$trans', picUrl='$picUrl', name='$name', id=$id, img1v1Id_str='$img1v1Id_str', alias=$alias)"
            }

        }

        override fun toString(): String {
            return "DataBean(subTime=$subTime, picId=$picId, picUrl='$picUrl', name='$name', id=$id, size=$size, msg=$msg, alias=$alias, artists=$artists, transNames=$transNames)"
        }

    }

    override fun toString(): String {
        return "AlbumSublistBean(count=$count, hasMore=$hasMore, paidCount=$paidCount, code=$code, data=$data)"
    }

}