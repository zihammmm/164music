package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseModel

class MineModel: BaseModel {
    fun getUserPlaylist(uid: Long) = ApiEngine.instance.getApiService().getUserPlaylist(uid)

    fun getIntelligenceList(id: Long, pid: Long) = ApiEngine.instance.getApiService().getIntelligenceList(id, pid)

    fun getMvSublist() = ApiEngine.instance.getApiService().getMvSublist()

    fun getArtistSublist() = ApiEngine.instance.getApiService().getArtistSublist()

    fun getAlbumSublistBean() = ApiEngine.instance.getApiService().getAlbumSublist()

    fun getMyFm() = ApiEngine.instance.getApiService().getMyFm()
}