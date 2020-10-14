package com.zihany.cloudmusic.song.mvvm.view

import android.animation.ObjectAnimator
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.model.SongInfo
import com.lzx.starrysky.utils.TimerTaskManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.LIKE_LIST
import com.zihany.cloudmusic.databinding.ActivitySongBinding
import com.zihany.cloudmusic.song.bean.LyricBean
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.song.mvvm.viewmodel.SongViewModel
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import com.zihany.cloudmusic.util.TimeUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class SongActivity : BaseActivity() {
    companion object {
        const val TAG = "SongActivity"
        const val SONG_INFO = "songInfo"
    }


    private var currentSongInfo: SongInfo? = null
    private var ids: Long? = null
    private var songDetail: SongDetailBean? = null
    private var timerTask: TimerTaskManager? = null
    private var isLike = false
    private var playMode: Int = 0
    private var rotateAnimator: ObjectAnimator? = null
    private var alphaAnimator: ObjectAnimator? = null
    private var isShowLyrics = false
    private var lyricBean: LyricBean? = null
    private val likeList by PreferenceUtils(LIKE_LIST, "")

    private val binding by binding<ActivitySongBinding>(R.layout.activity_song)
    private val viewModel by viewModel<SongViewModel>()

    private fun initTimerTaskWork() {
        timerTask!!.setUpdateProgressTask {
            Runnable {
                val position = MusicManager.getInstance().playingPosition
                binding.seekBar.progress = position.toInt()
                binding.tvPastTime.text = TimeUtil.getTimeNoYMDH(position)
                binding.lrc.updateTime(position)
            }
        }

    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarDarkFont(false)
                .init()
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }

    private fun getIntentData() {
        currentSongInfo = intent.getParcelableExtra(SONG_INFO)
    }

    private fun checkMusicState() {
        setSongInfo(currentSongInfo!!.songName, currentSongInfo!!.artist)
        val list = GsonUtil.instance.fromJson<List<String>>(likeList, List::class.java)
        
    }

    fun judgeContainsStr(cardNum: String?): Boolean {
        val regex = ".*[a-zA-Z]+.*"
        val m = Pattern.compile(regex).matcher(cardNum)
        return m.matches()
    }


}