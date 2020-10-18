package com.zihany.cloudmusic.song.mvvm.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.ImmersionBar
import com.lzx.starrysky.manager.MusicManager
import com.lzx.starrysky.model.SongInfo
import com.lzx.starrysky.utils.TimerTaskManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.LIKE_LIST
import com.zihany.cloudmusic.databinding.ActivitySongBinding
import com.zihany.cloudmusic.manager.SongPlayManager
import com.zihany.cloudmusic.song.bean.LyricBean
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.song.mvvm.viewmodel.SongViewModel
import com.zihany.cloudmusic.util.*
import com.zihany.cloudmusic.widget.LyricView
import jp.wasabeef.glide.transformations.BlurTransformation
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class SongActivity : BaseActivity() {
    companion object {
        const val TAG = "SongActivity"
        const val SONG_INFO = "songInfo"
    }

    private var currentSongInfo: SongInfo? = null
    private var ids = 0L
    private lateinit var timerTask: TimerTaskManager
    private var isLike = false
    private var playMode: Int = 0
    private lateinit var rotateAnimator: ObjectAnimator
    private lateinit var alphaAnimator: ObjectAnimator
    private var isShowLyrics = false
    private val likeList by PreferenceUtils(LIKE_LIST, "")

    private val binding by binding<ActivitySongBinding>(R.layout.activity_song)
    private val viewModel by viewModel<SongViewModel>()

    private fun initTimerTaskWork() {
        timerTask.setUpdateProgressTask {
            Runnable {
                val position = MusicManager.getInstance().playingPosition
                binding.seekBar.progress = position.toInt()
                binding.tvPastTime.text = position.getTimeNoYMDH()
                binding.lrc.updateTime(position)
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    SongPlayManager.instance.seekTo(it.progress.toLong())
                    binding.seekBar.progress = it.progress
                    binding.lrc.updateTime(it.progress.toLong())
                }
            }

        })

    }

    override fun initData() {
        rotateAnimator = ObjectAnimator.ofFloat(binding.ivRecord, "rotation", 360f).apply {
            duration = 25 * 1000
            interpolator = LinearInterpolator()
            repeatCount = 100000
            repeatMode = ValueAnimator.RESTART
        }

        alphaAnimator = ObjectAnimator.ofFloat(binding.ivBg, "alpha", 0f, 0.13f).apply {
            duration = 1500
            interpolator = AccelerateDecelerateInterpolator()
        }

        getIntentData()
        setBackBtn(getString(R.string.colorWhite))
        playMode = SongPlayManager.instance.mode
        timerTask = TimerTaskManager()
        initTimerTaskWork()

        checkMusicState()

    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask.removeUpdateProgressTask()
    }

    override fun initView() {
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarDarkFont(false)
                .init()

        val intent = Intent()

        binding.apply {
            rlCenter.setOnClickListener {
                isShowLyrics = true
                showLyrics(true)
            }

            ivPlay.setOnClickListener {
                LogUtil.d(TAG, "click play")
                when {
                    SongPlayManager.instance.isPlaying() -> {
                        LogUtil.d(TAG, "pause music")
                        SongPlayManager.instance.pauseMusic()
                    }
                    SongPlayManager.instance.isPaused() -> {
                        LogUtil.d(TAG, "play music")
                        SongPlayManager.instance.playMusic()
                    }
                    SongPlayManager.instance.isIdle() -> {
                        LogUtil.d(TAG, "idle: currentSongInfo:${currentSongInfo}")
                        currentSongInfo?.let { it1 -> SongPlayManager.instance.clickASong(it1) }
                    }
                }
            }

            ivLike.setOnClickListener {
                if (isLike) {
                    ToastUtils.show("没有找到取消喜欢的接口")
                } else {
                    TODO("likeMusic(ids)")
                }
            }

            ivDownload.setOnClickListener {
                ToastUtils.show("不能下载")
            }

            ivComment.setOnClickListener {
                viewModel.songDetail.value?.let {
                    intent.setClass(this@SongActivity, CommentActivity::class.java)
                    intent.putExtra(CommentActivity.NAME, it.songs[0].name)
                    intent.putExtra(CommentActivity.ID, it.songs[0].id)
                    intent.putExtra(CommentActivity.ARTIST, it.songs[0].ar[0].name)
                    intent.putExtra(CommentActivity.COVER, it.songs[0].al.picUrl)
                    intent.putExtra(CommentActivity.FROM, CommentActivity.SONG_COMMENT)
                    startActivity(intent)
                }
            }

            ivInfo.setOnClickListener {
                intent.setClass(this@SongActivity, SongDetailActivity::class.java)
                intent.putExtra(SONG_INFO, currentSongInfo)
                startActivity(intent)
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent)
            }

            ivPlayMode.setOnClickListener {
                when(playMode) {
                    SongPlayManager.MODE_LIST_LOOP_PLAY -> {
                        SongPlayManager.instance.mode = SongPlayManager.MODE_SINGLE_LOOP_PLAY
                        binding.ivPlayMode.setImageResource(R.drawable.shape_single_cycle)
                        playMode = SongPlayManager.MODE_SINGLE_LOOP_PLAY
                        ToastUtils.show("切换到单曲循环模式")
                    }
                    SongPlayManager.MODE_SINGLE_LOOP_PLAY -> {
                        SongPlayManager.instance.mode = SongPlayManager.MODE_RANDOM
                        binding.ivPlayMode.setImageResource(R.drawable.shape_list_random)
                        playMode = SongPlayManager.MODE_RANDOM
                        ToastUtils.show("切换到随机播放模式")
                    }
                    SongPlayManager.MODE_RANDOM -> {
                        SongPlayManager.instance.mode = SongPlayManager.MODE_LIST_LOOP_PLAY
                        binding.ivPlayMode.setImageResource(R.drawable.shape_list_cycle)
                        playMode = SongPlayManager.MODE_LIST_LOOP_PLAY
                        ToastUtils.show("切换到列表循环模式")
                    }
                }
            }

            ivPre.setOnClickListener {
                SongPlayManager.instance.playPreMusic()
            }

            ivNext.setOnClickListener {
                SongPlayManager.instance.playNextMusic()
            }

            ivList.setOnClickListener {
                intent.setClass(this@SongActivity, SongListActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent)
            }

        }
    }

    override fun startObserve() {
        viewModel.apply {
            songDetail.observe(this@SongActivity, Observer {
                onGetSongDetailSuccess(it)
            })

            getSongDetailError.observe(this@SongActivity, Observer {
                onGetSongDetailFail(it)
            })

            lyricBean.observe(this@SongActivity, Observer {
                onGetLyricSuccess(it)
            })

            getLyricError.observe(this@SongActivity, Observer {
                onGetLyricFail(it)
            })
        }
    }

    override fun onClick(view: View) {

    }

    private fun getIntentData() {
        currentSongInfo = intent.getParcelableExtra(SONG_INFO)
        LogUtil.d(TAG, "currentSongInfo: ${currentSongInfo!!.songId}, ${currentSongInfo!!.songName}")
    }

    private fun checkMusicState() {
        setSongInfo(currentSongInfo!!.songName, currentSongInfo!!.artist)
        if (judgeContainsStr(currentSongInfo!!.songId)) {
            binding.llInfo.visibility = View.GONE
        } else {
            if (viewModel.lyricBean.value == null) {
                viewModel.getLyric(currentSongInfo!!.songId.toLong())
            }
            binding.llInfo.visibility = View.VISIBLE
            ids = currentSongInfo!!.songId.toLong()
            val songId = currentSongInfo!!.songId
            LogUtil.d(TAG, "likeList: $likeList")
            val list = GsonUtil.instance.fromJson(likeList, List::class.java)
            if (list.contains(songId)) {
                isLike = true
                binding.ivLike.setImageResource(R.drawable.shape_like_white)
            } else {
                isLike = false
            }
            if (SongPlayManager.instance.getSongDetail(ids) == null) {
                LogUtil.d(TAG, "$ids song not exist")
                viewModel.getSongDetail(ids)
            } else {
                viewModel.songDetail.postValue(SongPlayManager.instance.getSongDetail(ids))
            }

            val duration = currentSongInfo!!.duration
            if (binding.seekBar.max.toLong() != duration) {
                binding.seekBar.max = duration.toInt()
            }

            when (playMode) {
                SongPlayManager.MODE_LIST_LOOP_PLAY -> {
                    binding.ivPlayMode.setImageResource(R.drawable.shape_list_cycle)
                }
                SongPlayManager.MODE_RANDOM -> {
                    binding.ivPlayMode.setImageResource(R.drawable.shape_list_random)
                }
                SongPlayManager.MODE_SINGLE_LOOP_PLAY -> {
                    binding.ivPlayMode.setImageResource(R.drawable.shape_single_cycle)
                }
            }
            binding.totalTime.text = duration.getTimeNoYMDH()
            checkMusicPlaying()
        }
    }

    private fun checkMusicPlaying() {
        timerTask.startToUpdateProgress()
        if (SongPlayManager.instance.isPlaying()) {
            LogUtil.d(TAG, "music is playing")
            rotateAnimator.let {
                when {
                    it.isPaused -> {
                        it.resume()
                    }
                    it.isRunning -> {

                    }
                    else -> {
                        it.start()
                    }
                }
            }
            binding.ivPlay.setImageResource(R.drawable.shape_pause)
        } else {
            LogUtil.d(TAG, "music is not playing")
            rotateAnimator.pause()
            binding.ivPlay.setImageResource(R.drawable.shape_play_white)
        }
    }

    private fun judgeContainsStr(cardNum: String?): Boolean {
        val regex = ".*[a-zA-Z]+.*"
        val m = Pattern.compile(regex).matcher(cardNum)
        return m.matches()
    }

    private fun setSongDetailBean(songDetail: SongDetailBean) {
        val coverUrl = songDetail.songs[0].al.picUrl
        LogUtil.d(TAG, "coverUrl: $coverUrl")
        Glide.with(this)
                .load(coverUrl)
                .placeholder(R.drawable.shape_record)
                .into(binding.ivRecord)

        Glide.with(this)
                .load(coverUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 12)))
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(binding.ivBg)

    }

    private fun showLyrics(isShowLyrics: Boolean) {
        binding.ivRecord.visibility = if (isShowLyrics) {
            View.GONE
        } else {
            View.VISIBLE
        }

        binding.lrc.visibility = if (isShowLyrics) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun onGetSongDetailSuccess(songDetailBean: SongDetailBean) {
        LogUtil.d(TAG, "onGetSongDetailSuccess: $songDetailBean")
        SongPlayManager.instance.putSongDetail(songDetailBean)
        setSongDetailBean(songDetailBean)
    }

    private fun onGetSongDetailFail(error: String) {
        LogUtil.e(TAG, "onGetSongDetailFail: $error")
    }

    private fun onGetLyricSuccess(lyricBean: LyricBean) {
        //LogUtil.d(TAG, "onGetLyricSuccess: $lyricBean")
        LogUtil.d(TAG, "main:${lyricBean.lrc?.lyric ?: ""}, second:${lyricBean.tlyric.lyric ?: ""}")
        binding.lrc.loadLrc(lyricBean.lrc?.lyric ?: "", lyricBean.tlyric.lyric ?: "")
        initLrcListener()
    }

    private fun initLrcListener() {
        binding.lrc.listener = object : LyricView.OnPlayClickListener {
            override fun onPlayClick(time: Long): Boolean {
                SongPlayManager.instance.seekTo(time)
                if (SongPlayManager.instance.isPaused()) {
                    SongPlayManager.instance.playMusic()
                } else if (SongPlayManager.instance.isIdle()) {
                    currentSongInfo?.let { SongPlayManager.instance.clickASong(it) }
                }
                return true
            }
        }

        binding.lrc.coverChangeListener = object : LyricView.OnCoverChangeListener {
            override fun onCoverChange() {
                showLyrics(false)
            }
        }
    }

    private fun onGetLyricFail(error: String) {

    }

}