package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository

class LikeListRepository : BaseRepository() {
//    suspend fun getLikeList(uid: Long) =
//            safeApiCall(call = {requestLikeList(uid)}, errorMessage = "")
//
//    private suspend fun requestLikeList(uid: Long) =
//            executeResponse(ApiEngine.instance.getApiService().getLikeList(uid))

    fun getLikeList(uid: Long) =
            ApiEngine.instance.getApiService().getLikeList(uid)
}