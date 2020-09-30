package com.zihany.cloudmusic.api

import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.*
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.search.bean.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL: String = "http://you local host"
    }

    @GET("login/cellphone")
    fun login(@Query("phone") phone: String, @Query("password") password: String): ApiResponse<LoginBean>

    @GET("layout")
    fun logout(): ApiResponse<LogoutBean>

    @GET("banner")
    fun getBanner(@Query("type") type: String): ApiResponse<BannerBean>

    @GET("recommend/resource")
    fun getRecommendPlayList(): ApiResponse<MainRecommendPlayListBean>

    @GET("recommend/songs")
    fun getDailyRecommend(): ApiResponse<DailyRecommendBean>

    @GET("likelist")
    fun getLikeList(@Query("id") uid: Long): ApiResponse<LikeListBean>

    @GET("recommend/songs")
    fun getRecommendPlay(): ApiResponse<DailyRecommendBean>

    @GET("search/hot/detail")
    fun getSearchHotDetail(): ApiResponse<HotSearchDetailBean>

    @GET("search")
    fun getSongSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<SongSearchBean>

    @GET("search")
    fun getFeedSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<FeedSearchBean>

    @GET("search")
    fun getSingerSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<SingerSearchBean>

    @GET("search")
    fun getAlbumSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<AlbumSearchBean>

    @GET("search")
    fun getPlayListSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<PlayListSearchBean>

    @GET("search")
    fun getRadioSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<RadioSearchBean>

    @GET("search")
    fun getUserSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<UserSearchBean>

    @GET("search")
    fun getSynthesisSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): ApiResponse<SynthesisSearchBean>

    @GET("user/playlist")
    fun getUserPlaylist(@Query("uid") uid: Long): ApiResponse<UserPlayListBean>

    @GET("playmode/intelligence/list")
    fun getIntelligenceList(@Query("id") id: Long, @Query("pid") pid: Long): ApiResponse<PlayModeIntelligenceBean>

    @GET("mv/sublist")
    fun getMvSublist(): ApiResponse<MvSublistBean>

    @GET("artist/sublist")
    fun getArtistSublist(): ApiResponse<ArtistSublistBean>

    @GET("album/sublist")
    fun getAlbumSublist(): ApiResponse<AlbumSublistBean>

    @GET("personal_fm")
    fun getMyFm(): ApiResponse<MyFmBean>
}