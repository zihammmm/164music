package com.zihany.cloudmusic.main.bean

import com.zihany.cloudmusic.personal.bean.UserEventBean

class MainEventBean {
    var code = 0
    var more = false
    var lastTime = 0L
    var event: ArrayList<UserEventBean.EventsBean>? = null

    override fun toString(): String {
        return "MainEventBean(code=$code, more=$more, lastTime=$lastTime, event=$event)"
    }

}