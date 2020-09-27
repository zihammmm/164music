package com.zihany.cloudmusic.search.mvvm.view

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.database.SearchHistoryDaoOp
import com.zihany.cloudmusic.databinding.ActivitySearchResultBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.search.bean.SearchHistoryBean
import com.zihany.cloudmusic.search.mvvm.view.fragments.*
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

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()

        pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
        fragments.add(SongSearchFragment("单曲"))
        fragments.add(FeedSearchFragment("视频"))
        fragments.add(SingerSearchFragment("歌手"))
        fragments.add(AlbumSearchFragment("专辑"))
        fragments.add(PlayListSearchFragment("歌单"))
        fragments.add(RadioSearchFragment("主播电台"))
        fragments.add(UserSearchFragment("用户"))
        pagerAdapter.init(fragments)
    }
}