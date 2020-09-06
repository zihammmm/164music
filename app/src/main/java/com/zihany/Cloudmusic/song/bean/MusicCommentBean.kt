package com.zihany.Cloudmusic.song.bean

class MusicCommentBean {
    var isMusician = false
    var cnum = 0
    var userId = 0L
    var moreHot = false
    var code = 0
    var total = 0
    var more = false
    var topComments: MutableList<*>? = null
    var hotComments: MutableList<CommentsBean>? = null
    var comments: MutableList<CommentsBean>? = null

    inner class CommentsBean {
        var user: UserBeanX? = null
        var pendantData: Any? = null
        var showFloorComment: Any? = null
        var status = 0
        var commentId = 0L
        var content: String? = null
        var time = 0L
        var likeCount = 0
        var expressionUrl: String? = null
        var commentLocationType = 0
        var parentCommentId = 0
        var decoration: DecorationBean? = null
        var repliedMark: Any? = null
        var liked = false
        var beReplied: MutableList<*>? = null

        inner class UserBeanX {
            var locationInfo: Any? = null
            var liveInfo: Any? = null
            var userId = 0
            var nickName: String? = null
            var authStatus = 0
            var avatarUrl: String? = null
            var experts: Any? = null
            var userType = 0
            var expertTags: Any? = null
            var vipRights: Any? = null
            var remarkName: Any? = null
            var vipType = 0
        }

        inner class DecorationBean {}

        override fun toString(): String {
            return "CommentsBean{" +
                    "user=${user}," +
                    "pendantData=${pendantData}," +
                    "status=${status}," +
                    "commentId=${commentId}," +
                    "content=${content}," +
                    "time=${time}," +
                    "likeCount=${likeCount}," +
                    "expressionUrl=${expressionUrl}," +
                    "commentLocationType=${commentLocationType}," +
                    "parentCommentId=${parentCommentId}," +
                    "decoration=${decoration}," +
                    "repliedMark=${repliedMark}," +
                    "liked=${liked}," +
                    "beReplied=${beReplied}}"
        }
    }
}