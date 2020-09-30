package com.zihany.cloudmusic.search.mvvm.view

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivitySingerBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel
import com.zihany.cloudmusic.util.AppBarStateChangeListener
import com.zihany.cloudmusic.util.DensityUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_singer.*

class SingerActivity: BaseActivity<SingerViewModel>() {
    companion object {
        const val TAG = "SingerActivity"
        const val SINGER_ID = "singerId"
        const val SINGER_PICURL = "singerPicUrl"
        const val SINGER_NAME = "singerName"
    }
    private lateinit var binding: ActivitySingerBinding
    private var fragments = ArrayList<BaseFragment<*>>()
    private var singId = 0L
    private var minDistance = 0f
    private var deltaDistance = 0f
    private lateinit var pagerAdapter: MultiFragmentPagerAdapter

    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))

        Glide.with(this)
                .load(intent.getStringExtra(SINGER_PICURL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivSingerCover)

        Glide.with(this)
                .load(intent.getStringExtra(SINGER_PICURL))
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
                .into(binding.ivSinger)

        binding.tvName.text = intent.getStringExtra(SINGER_NAME)
        singId = intent.getLongExtra(SINGER_ID, -1)
        setLeftTitleText(intent.getStringExtra(SINGER_NAME), getString(R.string.colorWhite))
        setLeftTitleTextColorWhite()

        if (singId != -1L) {

        }
        binding.vpContainer.adapter = pagerAdapter
        binding.vpContainer.offscreenPageLimit = 3
        binding.vpContainer.currentItem = 0
        pagerAdapter.getItem(0).userVisibleHint = true
        binding.tabTitle.setViewPager(binding.vpContainer)

        minDistance = DensityUtil.dp2px(this, 85f).toFloat()
        deltaDistance = DensityUtil.dp2px(this, 250f).toFloat()
    }

    override fun onResume() {
        super.onResume()

        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?) {
                val alphaPercent = (binding.rlInfo.top - minDistance) / deltaDistance
                binding.tvName.alpha = alphaPercent
                binding.ivSingerCover.imageAlpha = (alphaPercent * 255).toInt()
                if (alphaPercent <0.2f) {
                    val leftTitleAlpha = (1f - alphaPercent / 0.2f)
                    setLeftTitleAlpha(leftTitleAlpha)
                } else {
                    setLeftTitleAlpha(0f)
                }
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                TODO("Not yet implemented")
            }

        })
    }

}