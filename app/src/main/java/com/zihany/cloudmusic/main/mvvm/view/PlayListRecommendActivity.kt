package com.zihany.cloudmusic.main.mvvm.view

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
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
        TODO("Not yet implemented")
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivityPlayListRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()

        pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
        fragments.add()
    }
}