package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository

class IntelligenceListRepository: BaseRepository() {
//    suspend fun getIntelligenceList(id: Long, pid: Long) =
//            safeApiCall(call = { requestIntelligenceList(id, pid) }, errorMessage = "")
//
//    private suspend fun requestIntelligenceList(id: Long, pid: Long) =
//            executeResponse(ApiEngine.instance.getApiService().getIntelligenceList(id, pid))

    fun getIntelligenceList(id: Long, pid: Long) =
            ApiEngine.instance.getApiService().getIntelligenceList(id, pid)
}