package com.zihany.Cloudmusic.song

import android.animation.ObjectAnimator
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.model.SongInfo
import com.lzx.starrysky.utils.TimerTaskManager
import com.zihany.Cloudmusic.databinding.ActivitySongBinding
import com.zihany.Cloudmusic.song.bean.LyricBean
import com.zihany.Cloudmusic.song.bean.SongDetailBean
import com.zihany.Cloudmusic.util.TimeUtil


class SongActivity: AppCompatActivity() {
    companion object {
        private val TAG = "SongActivity"
        val SONG_INFO = "songInfo"
    }

//    private var ivRecord: CircleImageView
//    private var ivLike: ImageView
//    private var tvPastTime: TextView
//    private var tvTotalTime: TextView
//    private var seekBar: AppCompatSeekBar
//    private var ivPlay: ImageView
//    private var ivBg: ImageView
//    private var ivPlayMode: ImageView
//    private var llInfo: LinearLayout
//    private var lrc: LyricView
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
            val position = MusicManager.getInstance().playingPosition
            binding.seekBar.progress = position.toInt()
            binding.tvPastTime.text = TimeUtil.getTimeNoYMDH(position)
            binding.lrc.updateTime(position)
         }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }


}