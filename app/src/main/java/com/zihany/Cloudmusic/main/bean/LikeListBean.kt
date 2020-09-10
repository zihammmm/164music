package com.zihany.Cloudmusic.main.bean

class LikeListBean {
    var checkPoint = 0L
    var code = 0
    var ids: MutableList<Long>? = null

    override fun toString(): String {
        return "LikeListBean(checkPoint=$checkPoint, code=$code, ids=$ids)"
    }

}