package com.zihany.Cloudmusic.song.bean

class PlayListCommentBean {
    var isMusician = false
    var userId = false
    var moreHot = false
    var code = 0
    var total = 0
    var more = false
    var topComments: MutableList<*>? = null
    var hotComments: MutableList<MusicCommentBean.CommentsBean>? = null
    var comments: MutableList<MusicCommentBean.CommentsBean>? = null
}