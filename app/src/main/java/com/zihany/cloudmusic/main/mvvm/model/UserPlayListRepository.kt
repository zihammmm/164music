package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository

class UserPlayListRepository: BaseRepository() {
//    suspend fun getUserPlaylist(uid: Long) =
//            safeApiCall(call = { requestPlaylist(uid) }, errorMessage = "")
//
//    private suspend fun requestPlaylist(uid: Long) =
//            executeResponse(ApiEngine.instance.getApiService().getUserPlaylist(uid))
    fun getUserPlaylist(uid: Long) =
        ApiEngine.instance.getApiService().getUserPlaylist(uid)
}