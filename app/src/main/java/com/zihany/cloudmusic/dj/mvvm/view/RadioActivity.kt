package com.zihany.cloudmusic.dj.mvvm.view

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivityRadioDetailBinding
import com.zihany.cloudmusic.dj.bean.DjSubBean
import com.zihany.cloudmusic.dj.event.RidEvent
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.util.AppBarStateChangeListener
import com.zihany.cloudmusic.util.ClickUtil
import com.zihany.cloudmusic.util.DensityUtil
import com.zihany.cloudmusic.util.LogUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_radio_detail.*
import org.greenrobot.eventbus.EventBus

class RadioActivity: BaseActivity<DjViewModel>() {
    companion object {
        const val TAG = "RadioActivity"
        const val IS_SUB = "isSub"
        const val SUB_COUNT = "subCount"
        const val COVER_URL = "coverUrl"
        const val RADIO_NAME = "radioName"
        const val RID = "rid"
    }
    private val fragments = ArrayList<BaseFragment<*>>()
    private var minDistance = 0f
    private var deltaDistance = 0f
    private var rid = 0L
    private var isSub = false
    private lateinit var pagerAdapter: MultiFragmentPagerAdapter
    private lateinit var binding: ActivityRadioDetailBinding

    @SuppressLint("SetTextI18n")
    override fun initData() {
        setBackBtn("#ffffff")

        val intent = intent
        Glide.with(this)
                .load(intent.getStringExtra(COVER_URL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivDjCover)

        Glide.with(this)
                .load(intent.getStringExtra(COVER_URL))
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
                .into(binding.ivDjCover)
        binding.tvName.text = intent.getStringExtra(RADIO_NAME)
        setLeftTitleText(getIntent().getStringExtra(RADIO_NAME), "#ffffff")

        var count = intent.getIntExtra(SUB_COUNT, 0)
        val subCount = if (count >= 10000) {
            count /= 10000
            "${count}万"
        }else {
            count.toString()
        }
        binding.tvInfo.text = subCount + "人已订阅"
        rid = intent.getLongExtra(RID, 0)
        isSub = intent.getBooleanExtra(IS_SUB, false)
        if (isSub) {
            binding.tvHasSub.visibility = View.VISIBLE
            binding.tvSub.visibility = View.GONE
        }else {
            binding.tvHasSub.visibility = View.GONE
            binding.tvSub.visibility = View.VISIBLE
        }

        binding.vpContainer.adapter = pagerAdapter
        binding.vpContainer.offscreenPageLimit = 2
        binding.vpContainer.setCurrentItem(0, true)
        pagerAdapter.getItem(0).userVisibleHint = true
        binding.tabTitle.setViewPager(binding.vpContainer)

        minDistance = DensityUtil.dp2px(this@RadioActivity, 85f).toFloat()
        deltaDistance = DensityUtil.dp2px(this@RadioActivity, 250f) - minDistance

        EventBus.getDefault().postSticky(RidEvent(rid))
    }

    override fun onResume() {
        super.onResume()

        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?) {
                val alphaPercent = (binding.rlTop.top - minDistance) / deltaDistance
                binding.tvName.alpha = alphaPercent
                binding.tvInfo.alpha = alphaPercent
                binding.tvSub.alpha = alphaPercent
                binding.tvHasSub.alpha = alphaPercent
                binding.ivDjCover.imageAlpha = (alphaPercent * 255).toInt()
                if (alphaPercent < 0.2f) {
                    val leftTitleAlpha = (1f - alphaPercent / 0.2f)
                    setLeftTitleAlpha(leftTitleAlpha)
                }else {
                    setLeftTitleAlpha(0f)
                }
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                if (state == State.COLLAPSED) {
                    setLeftTitleAlpha(255f)
                }else if (state == State.EXPANDED) {
                    binding.tvName.alpha = 1f
                }
            }

        })
    }

    fun onSubDjSuccess(bean: DjSubBean) {
        hideDialog()
        LogUtil.d(TAG, "onSubDjSuccess: $bean")
        if (bean.code == 200) {
            isSub != isSub
            if (isSub) {
                binding.tvHasSub.visibility = View.VISIBLE
                binding.tvSub.visibility = View.GONE
            }else {
                tv_has_sub.visibility = View.GONE
                tv_sub.visibility = View.VISIBLE
            }
        }
    }

    fun onSubDjFail(msg: String) {
        hideDialog()
        LogUtil.e(TAG, "onSubDjFail: $msg")
    }

    inner class DjPresenter {
        fun onClickTvSub(v: View?) {
            if (ClickUtil.isFastClick(500, v)) {
                return
            }
            showDialog()
            viewModel.subDj(rid, 1)
        }

        fun onClickTvHasSub(v: View?) {
            if (ClickUtil.isFastClick(500, v)) {
                return
            }
            showDialog()
            viewModel.subDj(rid, 0)
        }
    }
}