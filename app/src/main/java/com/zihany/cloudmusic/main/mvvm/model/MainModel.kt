package com.zihany.cloudmusic.main.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseModel
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.bean.LogoutBean
import io.reactivex.rxjava3.core.Observable

class MainModel : BaseModel {

    fun logout(): Observable<LogoutBean> = ApiEngine.instance.getApiService().logout()

    fun getLikeList(uid: Long): Observable<LikeListBean> = ApiEngine.instance.getApiService().getLikeList(uid)
}