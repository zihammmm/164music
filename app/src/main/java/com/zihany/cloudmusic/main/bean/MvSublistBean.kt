package com.zihany.cloudmusic.main.bean

import com.zihany.cloudmusic.search.bean.MvBean

class MvSublistBean {
    var code = 0
    var hasMore = false
    var count = 0
    var data: MutableList<DataBean>? = null

    inner class DataBean {
        var type = 0
        var title = ""
        var durationms = 0L
        var playTime = 0
        var coverUrl = ""
        var vid = ""
        var aliaName: Any? = null
        var transName: Any? = null
        var alg: Any? = null
        var markTypes: Any? = null
        var creator: MutableList<MvBean.CreatorBean>? = null
    }
}