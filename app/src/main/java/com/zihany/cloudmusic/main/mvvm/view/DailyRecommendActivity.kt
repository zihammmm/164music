package com.zihany.cloudmusic.main.mvvm.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityDailyRecommendBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.adapter.SongListAdapter
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.SharePreferenceUtil
import com.zihany.cloudmusic.util.TimeUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import java.sql.Time

class DailyRecommendActivity: BaseActivity<WowViewModel>() {
    companion object {
        const val TAG = "DailyRecommendActivity"
    }
    private lateinit var binding: ActivityDailyRecommendBinding
    private var deltaDistance = 0
    private var minDistance = 0
    private lateinit var songAdapter: SongListAdapter

    @SuppressLint("SetTextI18n")
    override fun initData() {
        setLeftTitleText("每日推荐")
        setBackBtn("#ffffff")

        songAdapter = SongListAdapter(this)
        songAdapter.type = 1
        binding.rvDailyrecommend.layoutManager = LinearLayoutManager(this)
        binding.rvDailyrecommend.adapter = songAdapter

        val coverUrl = GsonUtil.fromJSON<LoginBean>(SharePreferenceUtil.getInstance(this).getUserInfo(""))
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

        binding.tvDay.text = TimeUtil.getDay(System.currentTimeMillis())
        binding.tvMonth.text = "/${TimeUtil.getMonth(System.currentTimeMillis())}"

        val updateTime = SharePreferenceUtil.getInstance(this)
                .getDailyUpdateTime()
        LogUtil.d(TAG, "上次日推更新时间: ${TimeUtil.getTimeStandard(System.currentTimeMillis())}")
        if (!TimeUtil.isOver7am(updateTime)) {
            TODO("0918")
        }else {

        }

    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivityDailyRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }


}