package com.zihany.cloudmusic.song.mvvm.view

import android.animation.ObjectAnimator
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.model.SongInfo
import com.lzx.starrysky.utils.TimerTaskManager
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivitySongBinding
import com.zihany.cloudmusic.song.bean.LyricBean
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.song.mvvm.viewmodel.SongViewModel
import com.zihany.cloudmusic.util.TimeUtil


class SongActivity : BaseActivity<SongViewModel>() {
    companion object {
        const val TAG = "SongActivity"
        const val SONG_INFO = "songInfo"
    }

    private var binding: ActivitySongBinding = ActivitySongBinding.inflate(layoutInflater)

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

    init {
        setContentView(binding.root)
    }

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


}