package com.zihany.cloudmusic.main.mvvm.view

import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivityPlayListRecommendBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel

class PlayListRecommendActivity: BaseActivity<WowViewModel>() {
    companion object {
        const val TAG = "PlayListRecommendActivity"
    }
    private var fragments = ArrayList<BaseFragment<*>>()
    private lateinit var pagerAdapter: MultiFragmentPagerAdapter
    private lateinit var binding: ActivityPlayListRecommendBinding


    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))
        setLeftTitleText(getString(R.string.playlist_playground), getString(R.string.colorWhite))
        binding.vpContainer.adapter = pagerAdapter
        binding.vpContainer.offscreenPageLimit = 6
        binding.vpContainer.currentItem = 0
        pagerAdapter.getItem(0).userVisibleHint = true
        binding.tabType.setViewPager(binding.vpContainer)
        initListener()
    }

    private fun initListener() {
        binding.tabType.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {

            }

        })

        binding.vpContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

        })
    }

}