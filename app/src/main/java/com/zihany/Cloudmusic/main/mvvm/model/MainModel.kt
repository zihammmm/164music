package com.zihany.Cloudmusic.main.mvvm.model

import com.zihany.Cloudmusic.api.ApiEngine
import com.zihany.Cloudmusic.main.bean.LikeListBean
import com.zihany.Cloudmusic.main.bean.LogoutBean
import io.reactivex.rxjava3.core.Observable

class MainModel {
    fun logout(): Observable<LogoutBean> = ApiEngine.instance.getApiService().logout()

    fun getLikeList(uid: Long): Observable<LikeListBean> = ApiEngine.instance.getApiService().getLikeList(uid)

}