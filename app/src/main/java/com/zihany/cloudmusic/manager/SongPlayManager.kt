package com.zihany.cloudmusic.manager

import android.widget.Toast
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.manager.OnPlayerEventListener
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.api.ApiService
import com.zihany.cloudmusic.base.LATEST_SONG
import com.zihany.cloudmusic.manager.bean.MusicCanPlayBean
import com.zihany.cloudmusic.manager.event.PauseMusicEvent
import com.zihany.cloudmusic.manager.event.StopMusicEvent
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import com.zihany.cloudmusic.util.ToastUtils
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.greenrobot.eventbus.EventBus
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.regex.Pattern
import kotlin.random.Random

class SongPlayManager private constructor() {
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SongPlayManager()
        }
        const val TAG = "SongPlayManager"
        const val CHECK_MUSIC_URL = "/check/music"
        const val MODE_LIST_LOOP_PLAY = 0x001
        const val MODE_SINGLE_LOOP_PLAY = 0x002
        const val MODE_RANDOM = 0x003
    }

    var mode = MODE_LIST_LOOP_PLAY
    private val songList: MutableList<SongInfo> = ArrayList()
    private var currentSongIndex = 0
    private var musicCanPlayMap: HashMap<String, Boolean> = HashMap()
    private var songListener: SongPlayListener
    private var songDetailMap: HashMap<Long, SongDetailBean> = HashMap()
    private var lateSong by PreferenceUtils(LATEST_SONG, SongInfo())

    init {
        musicCanPlayMap.clear()
        songList.clear()
        songDetailMap.clear()
        songListener = SongPlayListener()
        MusicManager.getInstance().addPlayerEventListener(songListener)
    }

    fun addSong(songInfo: SongInfo): Int {
        if (songList.contains(songInfo)) {
            for (i in songList.indices) {
                if (songInfo.songId == songList[i].songId) {
                    return i
                }
            }
        } else {
            songList.add(songInfo)
        }
        return songList.size - 1
    }

    fun deleteSong(position: Int) {
        songList.removeAt(position)
    }

    fun clearSongList() {
        songList.clear()
        currentSongIndex = 0
    }

    fun seekTo(progress: Long) {
        MusicManager.getInstance().seekTo(progress)
    }

    fun playPreMusic() {
        cancelPlay()
        when (mode) {
            MODE_LIST_LOOP_PLAY -> {
                if (currentSongIndex < songList.size) {
                    if (currentSongIndex == 0) {
                        currentSongIndex = songList.size - 1
                    } else {
                        currentSongIndex--
                    }
                    checkMusic(songList[currentSongIndex].songId)
                } else {
                    LogUtil.w(TAG, "currentSongIndex >= songListSize")
                }
            }
            MODE_SINGLE_LOOP_PLAY -> {
                playMusic(songList[currentSongIndex].songId)
            }
            MODE_RANDOM -> {
                val ra = Random
                var random = ra.nextInt(songList.size - 1)
                while (random == currentSongIndex) {
                    random = ra.nextInt(songList.size - 1)
                }
                currentSongIndex = random
                checkMusic(songList[currentSongIndex].songId)
            }
        }
    }

    fun checkMusic(songId: String) {
        LogUtil.d(TAG, "checkMusic: $songId")
        if (musicCanPlayMap[songId] == null) {
            LogUtil.d(TAG, "music can play map is null")
            setOnSongCanPlayListener(songId, object : OnSongListener {
                override fun onSongCanPlaySuccess(bean: MusicCanPlayBean) {
                    musicCanPlayMap[songId] = bean.success
                    playMusic(songId)
                }

                override fun onSongCanPlayFail(e: String) {
                    ToastUtils.show(e)
                }

            })
        } else {
            playMusic(songId)
        }
    }

    private fun setOnSongCanPlayListener(id: String, listener: OnSongListener?) {
        val requestBuilder = Request.Builder()
        val urlBuilder = (ApiService.BASE_URL + CHECK_MUSIC_URL).toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter("id", id)
        val request = requestBuilder.url(urlBuilder.build()).build()
        LogUtil.d(TAG, "request 请求头:${request}")
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.message?.let { listener?.onSongCanPlayFail(it) }
            }

            override fun onResponse(call: Call, response: Response) {
                val dstStr = getErrorCodeString(response)
                LogUtil.d(TAG, "dstStr: $dstStr")
                val bean = GsonUtil.instance.fromJson<MusicCanPlayBean>(dstStr, MusicCanPlayBean::class.java)
                if (bean != null) {
                    listener?.onSongCanPlaySuccess(bean)
                } else {
                    listener?.onSongCanPlayFail("response is null")
                }
            }
        })
    }

    private fun getErrorCodeString(response: Response): String {
        var res = ""
        val responseBody = response.body
        val contentLength = responseBody?.contentLength()
        if (!bodyEncoded(response.headers)) {
            val source = responseBody?.source()
            try {
                source?.request(Long.MAX_VALUE)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val buffer = source?.buffer
            var charset = Charsets.UTF_8
            val contentType = responseBody?.contentType()
            try {
                charset = contentType?.charset(Charsets.UTF_8)!!
            } catch (e: UnsupportedCharsetException) {
                e.message?.let { LogUtil.e(TAG, it) }
                e.printStackTrace()
            }
            if (contentLength != 0L) {
                res = buffer?.clone()?.readString(charset)!!
            }
        }
        return res
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && contentEncoding.equals("identity", true)
    }

    fun playMusic() {
        if (isPaused()) {
            MusicManager.getInstance().playMusic()
        }
    }

    fun playMusic(songId: String) {
        LogUtil.d(TAG, "playMusic: $songId")
        if (musicCanPlayMap[songId]!! || containsStr(songId)) {
            MusicManager.getInstance().playMusic(songList, currentSongIndex)
            App.getContext().let {
                lateSong = songList[currentSongIndex]
            }
        } else {
            Toast.makeText(App.getContext(), "本歌曲不能播放", Toast.LENGTH_LONG)
                    .show()
            if (mode != MODE_SINGLE_LOOP_PLAY) {
                playNextMusic()
            } else {
                EventBus.getDefault().post(StopMusicEvent(songList[currentSongIndex]))
            }
        }
    }

    fun playNextMusic() {
        LogUtil.d(TAG, "playNextMusic")
        cancelPlay()
        when (mode) {
            MODE_LIST_LOOP_PLAY -> {
                if (currentSongIndex < songList.size) {
                    if (currentSongIndex == songList.size - 1) {
                        currentSongIndex = 0
                    } else {
                        currentSongIndex++
                    }
                    checkMusic(songList[currentSongIndex].songId)
                } else {
                    LogUtil.w(TAG, "currentSongIndex >= songListSize")
                }
            }
            MODE_SINGLE_LOOP_PLAY -> {
                playMusic(songList[currentSongIndex].songId)
            }
            MODE_RANDOM -> {
                val ra = Random
                var random = ra.nextInt(songList.size - 1)
                while (random == currentSongIndex) {
                    random = ra.nextInt(songList.size - 1)
                }
                currentSongIndex = random
                checkMusic(songList[currentSongIndex].songId)
            }
        }
    }

    fun pauseMusic() {
        if (isPlaying()) {
            MusicManager.getInstance().pauseMusic()
        }
    }

    fun cancelPlay() {
        if (isPlaying() || isPaused()) {
            LogUtil.d(TAG, "cancel play")
            MusicManager.getInstance().stopMusic()
        }
    }

    fun isPaused() = MusicManager.getInstance().isPaused

    fun isPlaying() = MusicManager.getInstance().isPlaying

    fun clickPlayAll(songList: MutableList<SongInfo>, position: Int) {
        cancelPlay()
        addSongListAndPlay(songList, position)
    }

    fun addSongListAndPlay(songInfoList: MutableList<SongInfo>, index: Int) {
        if (songInfoList.isEmpty()) {
            return
        }
        addSongList(songInfoList, index)
        checkMusic(songInfoList[index].songId)
    }

    fun addSongList(songInfoList: MutableList<SongInfo>, index: Int) {
        clearSongList()
        songList.addAll(songInfoList)
        currentSongIndex = if (index >= songInfoList.size) {
            songInfoList.size - 1
        } else {
            index
        }
    }

    fun containsStr(cardNum: String): Boolean {
        val regex = ".*[a-zA-Z]+.*"
        val m = Pattern.compile(regex).matcher(cardNum)
        return m.matches()
    }

    fun clickASong(songInfo: SongInfo) {
        if (isPlaying()) {
            LogUtil.d(TAG, "isPlaying")
            if (!isCurMusicPlaying(songInfo.songId)) {
                LogUtil.d(TAG, "!isCurMusicPlaying")
                cancelPlay()
                addSongAndPlay(songInfo)
            }
        } else if (isPaused()) {
            LogUtil.d(TAG, "isPaused")
            if (!isCurMusicPlaying(songInfo.songId)) {
                cancelPlay()
                addSongAndPlay(songInfo)
            }
        } else if (isIdle()) {
            addSongAndPlay(songInfo)
        } else {
            LogUtil.d(TAG, "no idle, no playing, no paused. state: ${MusicManager.getInstance().state}")
        }
    }

    fun isIdle() = MusicManager.getInstance().isIdea

    fun isCurMusicPaused(songId: String) = MusicManager.getInstance().isCurrMusicIsPaused(songId)

    fun addSongAndPlay(songInfo: SongInfo) {
        currentSongIndex = addSong(songInfo)
        checkMusic(songInfo.songId)
    }

    fun getSongDetail(ids: Long) = songDetailMap[ids]

    fun putSongDetail(bean: SongDetailBean) = songDetailMap.put(bean.songs[0].dt, bean)

    fun isCurMusicPlaying(songId: String) = MusicManager.getInstance().isCurrMusicIsPlayingMusic(songId)

    interface OnSongListener {
        fun onSongCanPlaySuccess(bean: MusicCanPlayBean)
        fun onSongCanPlayFail(e: String)
    }

    inner class SongPlayListener : OnPlayerEventListener {
        override fun onPlayerStop() {
            LogUtil.d(TAG, "onPlayerStop")
        }

        override fun onMusicSwitch(songInfo: SongInfo?) {
            LogUtil.d(TAG, "onMusicSwitch")
        }

        override fun onPlayCompletion(songInfo: SongInfo?) {
            LogUtil.d(TAG, "songInfo:$songInfo")
            playNextMusic()
        }

        override fun onPlayerPause() {
            LogUtil.d(TAG, "onPlayerPause")
            EventBus.getDefault().post(PauseMusicEvent())
        }

        override fun onBuffering() {
            LogUtil.d(TAG, "onBuffering")
        }

        override fun onPlayerStart() {
            LogUtil.d(TAG, "onPlayerStart")
        }

        override fun onError(errorCode: Int, errorMsg: String?) {
            LogUtil.d(TAG, "onError:$errorCode, msg:$errorMsg")
            if (errorMsg != null) {
                ToastUtils.show(errorMsg)
            }
        }

    }
}