package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseRepository
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.bean.LogoutBean
import io.reactivex.rxjava3.core.Observable

class MainRepository : BaseRepository() {

    fun logout() = ApiEngine.instance.getApiService().logout()

    fun getLikeList(uid: Long) = ApiEngine.instance.getApiService().getLikeList(uid)
}