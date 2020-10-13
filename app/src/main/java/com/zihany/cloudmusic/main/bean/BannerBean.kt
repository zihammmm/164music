package com.zihany.cloudmusic.main.bean
data class BannerBean(
    val banners: List<Banner>,
    val code: Int
)

data class Banner(
        val adDispatchJson: Any,
        val adLocation: Any,
        val adSource: Any,
        val adid: Any,
        val encodeId: String,
        val event: Any,
        val exclusive: Boolean,
        val extMonitor: Any,
        val extMonitorInfo: Any,
        val monitorBlackList: Any,
        val monitorClick: Any,
        val monitorClickList: Any,
        val monitorImpress: Any,
        val monitorImpressList: Any,
        val monitorType: Any,
        val pic: String,
        val program: Any,
        val scm: String,
        val song: Any,
        val targetId: Int,
        val targetType: Int,
        val titleColor: String,
        val typeTitle: String,
        val url: Any,
        val video: Any
)
