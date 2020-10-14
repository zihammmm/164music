package com.zihany.cloudmusic.manager

import android.widget.Toast
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.manager.OnPlayerEventListener
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.base.LATEST_SONG
import com.zihany.cloudmusic.manager.bean.MusicCanPlayBean
import com.zihany.cloudmusic.manager.event.StopMusicEvent
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import com.zihany.cloudmusic.util.ToastUtils
import okhttp3.Request
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern

class SongPlayManager private constructor() {
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SongPlayManager()
        }
        const val TAG = "SongPlayManager"
        private val CHECK_MUSIC_URL = "check/music"
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

    fun checkMusic(songId: String) {
        if (musicCanPlayMap[songId] == null) {
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

    fun setOnSongCanPlayListener(id: String, listener: OnSongListener) {
        //TODO
        val requestBuilder = Request.Builder()

    }

    fun playMusic() {
        if (isPaused()) {
            MusicManager.getInstance().playMusic()
        }
    }

    fun playMusic(songId: String) {
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
            TODO("Not yet implemented")
        }

        override fun onMusicSwitch(songInfo: SongInfo?) {
            TODO("Not yet implemented")
        }

        override fun onPlayCompletion(songInfo: SongInfo?) {
            TODO("Not yet implemented")
        }

        override fun onPlayerPause() {
            TODO("Not yet implemented")
        }

        override fun onBuffering() {
            TODO("Not yet implemented")
        }

        override fun onPlayerStart() {
            TODO("Not yet implemented")
        }

        override fun onError(errorCode: Int, errorMsg: String?) {
            TODO("Not yet implemented")
        }

    }
}