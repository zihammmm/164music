package com.zihany.Cloudmusic.api

import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.bean.LogoutBean
import com.zihany.Cloudmusic.main.bean.MainRecommendPlayListBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    val BASE_URL: String
    get() = "http://you local host"

    @GET("login/cellphone")
    fun login(@Query("phone") phone: String, @Query("password") password: String): Observable<LoginBean>

    @GET("layout")
    fun logout(): Observable<LogoutBean>

    @GET("banner")
    fun getBanner(@Query("type") type: String)

    @GET("recommend/resource")
    fun getRecommendPlayList(): Observable<MainRecommendPlayListBean>

    @GET("recommend/songs")
}