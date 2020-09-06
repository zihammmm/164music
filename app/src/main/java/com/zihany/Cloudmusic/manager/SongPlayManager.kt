package com.zihany.Cloudmusic.manager

import android.widget.Toast
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.manager.OnPlayerEventListener
import com.lzx.starrysky.model.SongInfo
import com.zihany.Cloudmusic.App
import com.zihany.Cloudmusic.manager.bean.MusicCanPlayBean
import com.zihany.Cloudmusic.manager.event.StopMusicEvent
import com.zihany.Cloudmusic.song.bean.SongDetailBean
import com.zihany.Cloudmusic.util.LogUtil
import com.zihany.Cloudmusic.util.SharePreferenceUtil
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern

class SongPlayManager private constructor(){
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SongPlayManager
        }
        val TAG = "SongPlayManager"
        private val CHECK_MUSIC_URL = "check/music"
        val MODE_LIST_LOOP_PLAY = 0x001
        val MODE_SINGLE_LOOP_PLAY = 0x002
        val MODE_RANDOM = 0x003
    }

    private var mode = MODE_LIST_LOOP_PLAY
    private val songList: MutableList<SongInfo> = ArrayList()
    private var currentSongIndex = 0
    private var musicCanPlayMap: HashMap<String, Boolean> = HashMap()
    private var songListener: SongPlayListener
    private var songDetailMap: HashMap<Long, SongDetailBean> = HashMap()

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
        }else {
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

    fun checkMusic(songId: String) {
        if (musicCanPlayMap.get(songId) == null) {

        }
    }

    fun setOnSongCanPlayListener(id: String, listener: OnSongListener) {
        TODO("implementation")
    }

    fun playMusic(songId: String) {
        if (musicCanPlayMap[songId]!! || containsStr(songId)) {
            MusicManager.getInstance().playMusic(songList, currentSongIndex)
            App.getContext()?.let {
                SharePreferenceUtil.getInstance(it).saveLatestSong(songList[currentSongIndex]) }
        }else {
            Toast.makeText(App.getContext(), "本歌曲不能播放", Toast.LENGTH_LONG)
                    .show()
            if (mode != MODE_SINGLE_LOOP_PLAY) {
                playNextMusic()
            }else {
                EventBus.getDefault().post(StopMusicEvent(songList[currentSongIndex]))
            }
        }
    }

    fun playNextMusic() {

    }

    fun containsStr(cardNum: String): Boolean {
        val regex = ".*[a-zA-Z]+.*"
        val m = Pattern.compile(regex).matcher(cardNum)
        return m.matches()
    }

    interface OnSongListener {
        fun onSongCanPlaySuccess(bean: MusicCanPlayBean)
        fun onSongCanPlayFail(e: String)
    }

    inner class SongPlayListener: OnPlayerEventListener {
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