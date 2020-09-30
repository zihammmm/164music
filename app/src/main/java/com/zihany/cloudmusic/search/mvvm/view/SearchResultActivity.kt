package com.zihany.cloudmusic.search.mvvm.view

import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.database.SearchHistoryDaoOp
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
        setBackBtn(getString(R.string.colorWhite))
        setRightSearchButton()
        setEditText(getString(R.string.colorTransWithe))

        binding.vpContainer.adapter = pagerAdapter
        binding.vpContainer.offscreenPageLimit = 6
        binding.tablayout.setViewPager(binding.vpContainer)
        binding.tablayout.currentTab = 0

        if (SearchHistoryDaoOp.queryAll() != null) {
            stringList = SearchHistoryDaoOp.queryAll() as ArrayList<SearchHistoryBean>
        }

        intent.getStringExtra(SearchActivity.KEYWORDS)?.let {
            binding.commonTitle.etSearch.setText(it)
        }

    }

}