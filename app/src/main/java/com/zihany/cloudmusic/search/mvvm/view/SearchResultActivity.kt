package com.zihany.cloudmusic.search.mvvm.view

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivitySearchResultBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.search.bean.SearchHistoryBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class SearchResultActivity: BaseActivity<SearchViewModel>() {
    companion object {
        const val TAG = "SearchResultActivity"
    }
    private var fragments = ArrayList<BaseFragment<*>>()
    private var stringList = ArrayList<SearchHistoryBean>()
    private lateinit var pagerAdapter: MultiFragmentPagerAdapter
    private var keywords = ""
    private lateinit var binding: ActivitySearchResultBinding

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()

        pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
    }
}