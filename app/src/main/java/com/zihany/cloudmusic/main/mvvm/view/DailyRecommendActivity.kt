package com.zihany.cloudmusic.main.mvvm.view

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.DAILY_UPDATE_TIME
import com.zihany.cloudmusic.base.USER_INFO
import com.zihany.cloudmusic.database.DailyRecommendDaoOp
import com.zihany.cloudmusic.databinding.ActivityDailyRecommendBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.adapter.SongListAdapter
import com.zihany.cloudmusic.main.bean.DRGreenDaoBean
import com.zihany.cloudmusic.util.*
import jp.wasabeef.glide.transformations.BlurTransformation

class DailyRecommendActivity : BaseActivity() {
    companion object {
        const val TAG = "DailyRecommendActivity"
    }

    private lateinit var binding: ActivityDailyRecommendBinding
    private var deltaDistance = 0
    private var minDistance = 0
    private lateinit var songAdapter: SongListAdapter
    private lateinit var greenDaoList: List<DRGreenDaoBean>
    private var songInfos = ArrayList<SongInfo>()
    private val userInfo by PreferenceUtils(USER_INFO, "")

    @SuppressLint("SetTextI18n")
    override fun initData() {
        setLeftTitleText("每日推荐")
        setBackBtn("#ffffff")

        songAdapter = SongListAdapter(this)
        songAdapter.type = 1
        binding.rvDailyrecommend.layoutManager = LinearLayoutManager(this)
        binding.rvDailyrecommend.adapter = songAdapter

        val coverUrl = GsonUtil.fromJSON<LoginBean>(userInfo)
                ?.profile?.backgroundUrl

        coverUrl?.let {
            Glide.with(this)
                    .load(it)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivBackgroundCover)

            Glide.with(this)
                    .load(coverUrl)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivBackground)
        }

        val currentTime = System.currentTimeMillis()
        binding.tvDay.text = currentTime.getDay()
        binding.tvMonth.text = "/${currentTime.getMonth()}"

        val updateTime by PreferenceUtils(DAILY_UPDATE_TIME, System.currentTimeMillis())
        LogUtil.d(TAG, "上次日推更新时间: ${currentTime.getTimeStandard()}")
        if (!updateTime.isOver7am()) {
            DailyRecommendDaoOp.deleteAllData()
        } else {
            greenDaoList = DailyRecommendDaoOp.queryAll()
            notifyAdapter(greenDaoList)
        }

        minDistance = dp2px(85f)
        deltaDistance = dp2px(200f) - minDistance
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }

    private fun notifyAdapter(greenDaoList: List<DRGreenDaoBean>) {
        songInfos.clear()
        for (item: DRGreenDaoBean in greenDaoList) {
            val songInfo = SongInfo()
            songInfo.songCover = item.songCover
            songInfo.songName = item.songName
            songInfo.duration = item.duration
            songInfo.artist = item.artist
            songInfo.songId = item.songId
            songInfo.songUrl = item.songUrl
            songInfo.artistId = item.artistId
            songInfo.artistKey = item.artistAvatar
            songInfos.add(songInfo)
        }
        songAdapter.notifyDataSetChanged(songInfos)
    }

    override fun onResume() {
        super.onResume()
        initAppBarLayoutListener()
    }

    private fun initAppBarLayoutListener() {
        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?) {
                val alphaPercent = (binding.rlPlay.top - minDistance) / deltaDistance.toFloat()
                val alpha = alphaPercent * 255
                binding.ivBackgroundCover.imageAlpha = alpha.toInt()
                binding.tvMonth.alpha = alphaPercent
                binding.tvDay.alpha = alphaPercent
                setLeftTitleTextColorWhite()
                if (alphaPercent < 0.2f) {
                    val leftTitleAlpha = (1.0f - alphaPercent / 0.2f)
                    setLeftTitleAlpha(leftTitleAlpha)
                } else {
                    setLeftTitleAlpha(0f)
                }
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                if (state == State.COLLAPSED) {
                    setLeftTitleAlpha(255f)
                }
            }

        })
    }


}