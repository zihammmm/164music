package com.zihany.cloudmusic.main.mvvm.view

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityPlayListBinding
import com.zihany.cloudmusic.main.adapter.SongListAdapter
import com.zihany.cloudmusic.main.bean.PlaylistDetailBean
import com.zihany.cloudmusic.main.bean.Track
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.main.mvvm.viewmodel.PlayListViewModel
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.manager.SongPlayManager
import com.zihany.cloudmusic.song.mvvm.view.CommentActivity
import com.zihany.cloudmusic.util.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_play_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActivity : BaseActivity() {
    companion object {
        const val TAG = "PlayListActivity"
    }

    private lateinit var adapter: SongListAdapter
    private var beanList = ArrayList<Track>()

    private var songInfos = ArrayList<SongInfo>()
    private var position = -1
    var deltaDistance = 0
    var minDistance = 0
    private var alphaAnimator: ObjectAnimator? = null
    private var coverAlphaAnimator: ObjectAnimator? = null
    private val binding by binding<ActivityPlayListBinding>(R.layout.activity_play_list)
    private val viewModel by viewModel<PlayListViewModel>()

    override fun initData() {
        setBackBtn("#ffffff")
        setLeftTitleTextColorWhite()
        setLeftTitleText("歌单")
        beanList.clear()

        viewModel.apply {
            playlistPicUrl.set(intent.getStringExtra(WowFragment.PLAYLIST_PICURL))
            playlistName.set(intent.getStringExtra(WowFragment.PLAYLIST_NAME))
            creatorName.set(intent.getStringExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME))
            creatorUrl.set(intent.getStringExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL))
            playlistId.set(intent.getLongExtra(WowFragment.PLAYLIST_ID, 0))
        }

        Glide.with(this)
                .load(viewModel.playlistPicUrl.get())
                .into(binding.ivCover)

        Glide.with(this)
                .load(viewModel.creatorUrl.get())
                .into(binding.ivCreatorAvatar)


        calculateColors(viewModel.playlistPicUrl.get()!!)
        Glide.with(this)
                .load(viewModel.playlistPicUrl.get())
                .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 10)))
                .into(binding.ivCoverBg)

        showDialog()
        viewModel.getPlayListDetail()

        minDistance = dp2px(85f)
        deltaDistance = application.applicationContext.dp2px(300f) - minDistance
    }

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        adapter = SongListAdapter(this)
        adapter.type = 2
        binding.run {
            adapter = this@PlayListActivity.adapter
            vm = viewModel

            rlPlayall.setOnClickListener {
                onClick(it)
            }

            rlDownload.setOnClickListener {
                onClick(it)
            }

            rlComment.setOnClickListener {
                onClick(it)
            }
        }
    }

    override fun startObserve() {
        viewModel.apply {
            bean.observe(this@PlayListActivity, androidx.lifecycle.Observer {
                onGetPlaylistDetailSuccess(it)
            })

            error.observe(this@PlayListActivity, androidx.lifecycle.Observer {
                onGetPlaylistDetailFail(it)
            })

        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.rl_playall -> {
                SongPlayManager.instance.clickPlayAll(songInfos, 0)
            }
            R.id.rl_download -> {
                ToastUtils.show("不能下载")
            }
            R.id.rl_comment -> {
                ToastUtils.show("还没写")
            }
        }
    }

    private fun calculateColors(url: String) {
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
                binding.apply {
                    ivCover.alpha = alphaPercent
                    ivCreatorAvatar.alpha = alphaPercent
                    tvPlaylistName.alpha = alphaPercent
                    tvCreatorName.alpha = alphaPercent
                }
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

    private fun onGetPlaylistDetailFail(e: String) {
        hideDialog()
        LogUtil.e(TAG, "onGetPlaylistDetailFail: $e")
        ToastUtils.show(e)
    }

    private fun onGetPlaylistDetailSuccess(bean: PlaylistDetailBean) {
        hideDialog()
        LogUtil.d(TAG, "onGetPlaylistDetailSuccess")
        if (!TextUtils.isEmpty(viewModel.creatorUrl.get())) {
            Glide.with(this)
                    .load(bean.playlist.creator.avatarUrl)
                    .into(binding.ivCreatorAvatar)
        }
        beanList.addAll(bean.playlist.tracks)
        songInfos.clear()
        for (i : Track in beanList) {
            val beanInfo = SongInfo()
            beanInfo.artist = i.ar[0].name
            beanInfo.songName = i.name
            beanInfo.songCover = i.al.picUrl
            beanInfo.songId = i.id.toString()
            beanInfo.duration = i.dt
            beanInfo.songUrl = "${SONG_URL}${i.id}.mp3"
            beanInfo.artistId = i.ar[0].id.toString()
            beanInfo.artistKey = i.al.picUrl
            songInfos.add(beanInfo)
        }
        adapter.notifyDataSetChanged(songInfos)
        binding.apply {
            tvShare.text = bean.playlist.shareCount.toString()
            tvComment.text = bean.playlist.commentCount.toString()
        }
    }
}