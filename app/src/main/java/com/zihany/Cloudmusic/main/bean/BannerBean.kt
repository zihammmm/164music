package com.zihany.Cloudmusic.main.bean

class BannerBean {
    var code = 0
    var banners: MutableList<BannersBean>? = null

    inner class BannersBean {
        var pic: String? = null
        var targetId: Long = 0
        var adid: Any? = null
        var targetType = 0
        var titleColor: String? = null
        var typeTitle: String? = null
        var url: String? = null
        var adurlV2: Any? = null
        var exclusive = false
        var monitorImpress: Any? = null
        var monitorClick: Any? = null
        var monitorType: Any? = null
        var monitorBlackList: Any? = null
        var extMonitor: Any? = null
        var extMonitorInfo: Any? = null
        var adSource: Any? = null
        var adLocation: Any? = null
        var encodeId: String? = null
        var program: Any? = null
        var event: Any?= null
        var video: Any? = null
        var song: Any? = null
        var bannerId: String? = null
        var alg: Any? = null
        var scm: String? = null
        var requestId: String? = null
        var showAdTag = false
        var pid: Any? = null
        var monitorImpressList: MutableList<*>? = null
        var monitorClickList: MutableList<*>? = null

        override fun toString(): String {
            return "BannersBean(pic=$pic, targetId=$targetId, adid=$adid, targetType=$targetType, titleColor=$titleColor, typeTitle=$typeTitle, url=$url, adurlV2=$adurlV2, exclusive=$exclusive, monitorImpress=$monitorImpress, monitorClick=$monitorClick, monitorType=$monitorType, monitorBlackList=$monitorBlackList, extMonitor=$extMonitor, extMonitorInfo=$extMonitorInfo, adSource=$adSource, adLocation=$adLocation, encodeId=$encodeId, program=$program, event=$event, video=$video, song=$song, bannerId=$bannerId, alg=$alg, scm=$scm, requestId=$requestId, showAdTag=$showAdTag, pid=$pid, monitorImpressList=$monitorImpressList, monitorClickList=$monitorClickList)"
        }

    }

    override fun toString(): String {
        return "BannerBean(code=$code, banners=$banners)"
    }


}