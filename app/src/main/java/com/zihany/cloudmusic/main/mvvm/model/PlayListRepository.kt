package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository

class PlayListRepository: BaseRepository() {
    fun getPlayListDetail(id: Long) =
            ApiEngine.instance.getApiService().getPlaylistDetail(id)
}