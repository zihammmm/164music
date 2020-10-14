package com.zihany.cloudmusic.song.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository

class SongRepository: BaseRepository() {

    fun getSongDetail(ids: Long) = ApiEngine.instance.getApiService().getSongDetail(ids)

    fun getLyric(id: Long) = ApiEngine.instance.getApiService().getLyric(id)
}