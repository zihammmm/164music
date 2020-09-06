package com.zihany.Cloudmusic.manager.bean

class MusicCanPlayBean {
    var success = false
    var message: String? = null

    override fun toString(): String {
        return "MusicCanPlayBean{success=${success}, message='${message}'}"
    }
}