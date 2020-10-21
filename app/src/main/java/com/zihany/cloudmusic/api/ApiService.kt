package com.zihany.cloudmusic.api

import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.*
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.search.bean.*
import com.zihany.cloudmusic.song.bean.LyricBean
import com.zihany.cloudmusic.song.bean.SongDetailBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL: String = "http://118.178.89.205:3000"
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
    suspend fun getDailyRecommend(): Observable<DailyRecommendBean>

    @GET("likelist")
    fun getLikeList(@Query("id") uid: Long): Observable<LikeListBean>

    @GET("recommend/songs")
    suspend fun getRecommendPlay(): Observable<DailyRecommendBean>

    @GET("search/hot/detail")
    suspend fun getSearchHotDetail(): Observable<HotSearchDetailBean>

    @GET("search")
    suspend fun getSongSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<SongSearchBean>

    @GET("search")
    suspend fun getFeedSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<FeedSearchBean>

    @GET("search")
    suspend fun getSingerSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<SingerSearchBean>

    @GET("search")
    suspend fun getAlbumSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<AlbumSearchBean>

    @GET("search")
    suspend fun getPlayListSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<PlayListSearchBean>

    @GET("search")
    fun getRadioSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<RadioSearchBean>

    @GET("search")
    fun getUserSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<UserSearchBean>

    @GET("search")
    fun getSynthesisSearch(@Query("keywords") keywords: String?, @Query("type") type: Int): Observable<SynthesisSearchBean>

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

    @GET("playlist/detail")
    fun getPlaylistDetail(@Query("id") id: Long): Observable<PlaylistDetailBean>

    @GET("song/detail")
    fun getSongDetail(@Query("ids") ids: Long): Observable<SongDetailBean>

    @GET("lyric")
    fun getLyric(@Query("id") id: Long): Observable<LyricBean>
}