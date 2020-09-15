package com.zihany.cloudmusic.api

import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.*
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.search.bean.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL: String = "http://you local host"
    }

    @GET("login/cellphone")
    fun login(@Query("phone") phone: String, @Query("password") password: String): Observable<LoginBean>

    @GET("layout")
    fun logout(): Observable<LogoutBean>

    @GET("banner")
    fun getBanner(@Query("type") type: String): Observable<BannerBean>

    @GET("recommend/resource")
    fun getRecommendPlayList(): Observable<MainRecommendPlayListBean>

    @GET("recommend/songs")
    fun getDailyRecommend(): Observable<DailyRecommendBean>

    @GET("likelist")
    fun getLikeList(@Query("id") uid: Long): Observable<LikeListBean>

    @GET("recommend/songs")
    fun getRecommendPlay(): Observable<DailyRecommendBean>

    @GET("search/hot/detail")
    fun getSearchHotDetail(): Observable<HotSearchDetailBean>

    @GET("search")
    fun getSongSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<SongSearchBean>

    @GET("search")
    fun getFeedSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<FeedSearchBean>

    @GET("search")
    fun getSingerSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<SingerSearchBean>

    @GET("search")
    fun getAlbumSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<AlbumSearchBean>

    @GET("search")
    fun getPlayListSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<PlayListSearchBean>

    @GET("search")
    fun getRadioSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<RadioSearchBean>

    @GET("search")
    fun getUserSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<UserSearchBean>

    @GET("search")
    fun getSynthesisSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): io.reactivex.Observable<SynthesisSearchBean>

    @GET("user/playlist")
    fun getUserPlaylist(@Query("uid") uid: Long): Observable<UserPlayListBean>

    @GET("playmode/intelligence/list")
    fun getIntelligenceList(@Query("id") id: Long, @Query("pid") pid: Long): Observable<PlayModeIntelligenceBean>

    @GET("mv/sublist")
    fun getMvSublist(): Observable<MvSublistBean>

    @GET("artist/sublist")
    fun getArtistSublist(): Observable<ArtistSublistBean>

    @GET("album/sublist")
    fun getAlbumSublist(): Observable<AlbumSublistBean>

    @GET("personal_fm")
    fun getMyFm(): Observable<MyFmBean>
}