package com.zihany.cloudmusic.song.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivitySongDetailBinding
import com.zihany.cloudmusic.song.mvvm.viewmodel.SongViewModel

class SongDetailActivity: BaseActivity<SongViewModel>() {

    private lateinit var binding: ActivitySongDetailBinding
    private var toTranslateIn: Animation? = null
    private var toTranslateOut: Animation? = null

    private lateinit var songInfo: SongInfo
    private lateinit var singerName: String
    private var singerId = 0L
    private lateinit var singerPicurl: String
    private var songId = 0L

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val intent = Intent()
        songInfo = intent.getParcelableExtra(SongActivity.SONG_INFO)
        singerId = songInfo.artistId.toLong()
        singerName = songInfo.artist
        singerPicurl = songInfo.artistKey

        Glide.with(this)
                .load(songInfo.songCover)
                .into(binding.ivCover)
        binding.tvSongname.text = "歌名:${songInfo.songName}"
        binding.mdSinger.setText("歌手:${singerName}")
        binding.tvSinger.text = singerName
        songId = songInfo.songId.toLong()
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivitySongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        if (toTranslateIn == null) {
            toTranslateIn = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_in)
            toTranslateIn!!.fillAfter = true
            toTranslateIn!!.startOffset = 200
        }
        if (toTranslateOut == null) {
            toTranslateOut = AnimationUtils.loadAnimation(this, R.anim.view_to_translate_out)
            toTranslateOut!!.fillAfter = true
        }
    }

    override fun onStart() {
        super.onStart()
        binding.view.startAnimation(toTranslateIn)
    }

    override fun finish() {
        super.finish()
        binding.view.startAnimation(toTranslateOut)
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out)
    }
}