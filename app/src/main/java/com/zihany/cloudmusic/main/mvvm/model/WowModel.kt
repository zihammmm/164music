package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseModel

class WowModel: BaseModel {
    fun getBanner() = ApiEngine.instance.getApiService().getBanner("2")

    fun getRecommendPlayList() = ApiEngine.instance.getApiService().getRecommendPlayList()
}