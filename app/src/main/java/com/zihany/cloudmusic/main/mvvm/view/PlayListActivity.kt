package com.zihany.cloudmusic.main.mvvm.view

import android.animation.ObjectAnimator
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityPlayListBinding
import com.zihany.cloudmusic.main.adapter.SongListAdapter
import com.zihany.cloudmusic.main.bean.PlaylistDetailBean
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.util.AppBarStateChangeListener
import com.zihany.cloudmusic.util.DensityUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_play_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class PlayListActivity : BaseActivity<WowViewModel>() {
    companion object {
        const val TAG = "PlayListActivity"
    }

    private var adapter: SongListAdapter? = null
    private var beanList = ArrayList<PlaylistDetailBean.PlaylistBean.TracksBean>()

    private var songInfos: List<SongInfo> = ArrayList()
    private var playlistId: Long = 0
    private var position = -1
    var deltaDistance = 0
    var minDistance = 0
    private var creatorUrl: String? = null
    private var alphaAnimator: ObjectAnimator? = null
    private var coverAlphaAnimator: ObjectAnimator? = null
    private var playlistName: String? = null
    private var playlistPicUrl: String? = null
    private var creatorName: String? = null
    private lateinit var binding: ActivityPlayListBinding

    override fun initData() {
        setBackBtn("#ffffff")
        setLeftTitleTextColorWhite()
        setLeftTitleText("歌单")
        beanList.clear()

        adapter = SongListAdapter(this)
        adapter!!.type = 2
        binding.rvPlaylistSong.layoutManager = LinearLayoutManager(this)
        binding.rvPlaylistSong.adapter = adapter

        playlistPicUrl = intent.getStringExtra(WowFragment.PLAYLIST_PICURL)
        Glide.with(this)
                .load(playlistPicUrl)
                .into(binding.ivCover)
        playlistName = intent.getStringExtra(WowFragment.PLAYLIST_NAME)
        binding.tvPlaylistName.text = playlistName
        creatorName = intent.getStringExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME)
        binding.tvCreatorName.text = creatorName
        creatorUrl = intent.getStringExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL)
        Glide.with(this)
                .load(creatorUrl)
                .into(binding.ivCreatorAvatar)
        playlistId = intent.getLongExtra(WowFragment.PLAYLIST_ID, 0)

        calculateColors(playlistPicUrl!!)

        Glide.with(this)
                .load(playlistPicUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 10)))
                .into(binding.ivCoverBg)

        binding.ivCoverBg.alpha = 0f
        showDialog()

        minDistance = DensityUtil.dp2px(this, 85f)
        deltaDistance = DensityUtil.dp2px(application.applicationContext, 300f) - minDistance
    }

    fun calculateColors(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val fileUrl = URL(url)
            val conn = fileUrl.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()

            val input = conn.inputStream
            val opt = BitmapFactory.Options()
            opt.inSampleSize = 270
            val bitmap = BitmapFactory.decodeStream(input, Rect(), opt)
            withContext(Dispatchers.Main) {
                background.background = BitmapDrawable(resources, bitmap)
                getAlphaAnimatorBg().start()
                getAlphaAnimatorCover().start()
            }
        }
    }

    private fun getAlphaAnimatorBg(): ObjectAnimator {
        if (alphaAnimator == null) {
            alphaAnimator = ObjectAnimator.ofFloat(binding.background, "alpha", 0f, 0.5f)
            alphaAnimator!!.duration = 1500
            alphaAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        }
        return alphaAnimator!!
    }

    private fun getAlphaAnimatorCover(): ObjectAnimator {
        if (coverAlphaAnimator == null) {
            coverAlphaAnimator = ObjectAnimator.ofFloat(binding.background, "alpha", 0f, 0.5f)
            coverAlphaAnimator!!.duration = 1500
            coverAlphaAnimator!!.interpolator = AccelerateDecelerateInterpolator()
        }
        return coverAlphaAnimator!!
    }

    override fun onResume() {
        super.onResume()
        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?) {
                val alphaPercent = (binding.llPlay.top - minDistance) / deltaDistance.toFloat()
                binding.ivCover.alpha = alphaPercent
                binding.ivCreatorAvatar.alpha = alphaPercent
                binding.tvPlaylistName.alpha = alphaPercent
                binding.tvCreatorName.alpha = alphaPercent
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimator?.let {
            if (it.isRunning) {
                it.cancel()
            }
            alphaAnimator = null
        }

        coverAlphaAnimator?.let {
            if (it.isRunning) {
                it.cancel()
            }
            coverAlphaAnimator = null
        }

    }
}