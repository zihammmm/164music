package com.zihany.cloudmusic.api

import androidx.lifecycle.LiveData
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
    fun login(@Query("phone") phone: String, @Query("password") password: String): LiveData<LoginBean>

    @GET("layout")
    fun logout(): LiveData<LogoutBean>

    @GET("banner")
    fun getBanner(@Query("type") type: String): LiveData<BannerBean>

    @GET("recommend/resource")
    fun getRecommendPlayList(): LiveData<MainRecommendPlayListBean>

    @GET("recommend/songs")
    fun getDailyRecommend(): LiveData<DailyRecommendBean>

    @GET("likelist")
    fun getLikeList(@Query("id") uid: Long): LiveData<LikeListBean>

    @GET("recommend/songs")
    fun getRecommendPlay(): LiveData<DailyRecommendBean>

    @GET("search/hot/detail")
    fun getSearchHotDetail(): LiveData<HotSearchDetailBean>

    @GET("search")
    fun getSongSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<SongSearchBean>

    @GET("search")
    fun getFeedSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<FeedSearchBean>

    @GET("search")
    fun getSingerSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<SingerSearchBean>

    @GET("search")
    fun getAlbumSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<AlbumSearchBean>

    @GET("search")
    fun getPlayListSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<PlayListSearchBean>

    @GET("search")
    fun getRadioSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<RadioSearchBean>

    @GET("search")
    fun getUserSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<UserSearchBean>

    @GET("search")
    fun getSynthesisSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): LiveData<SynthesisSearchBean>

    @GET("user/playlist")
    fun getUserPlaylist(@Query("uid") uid: Long): LiveData<UserPlayListBean>

    @GET("playmode/intelligence/list")
    fun getIntelligenceList(@Query("id") id: Long, @Query("pid") pid: Long): LiveData<PlayModeIntelligenceBean>

    @GET("mv/sublist")
    fun getMvSublist(): LiveData<MvSublistBean>

    @GET("artist/sublist")
    fun getArtistSublist(): LiveData<ArtistSublistBean>

    @GET("album/sublist")
    fun getAlbumSublist(): LiveData<AlbumSublistBean>

    @GET("personal_fm")
    fun getMyFm(): LiveData<MyFmBean>
}