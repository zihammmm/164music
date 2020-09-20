package com.zihany.cloudmusic.search.mvvm.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.database.SearchHistoryDaoOp
import com.zihany.cloudmusic.databinding.ActivitySearchBinding
import com.zihany.cloudmusic.search.adapter.HotSearchAdapter
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class SearchActivity: BaseActivity<SearchViewModel>() {
    companion object {
        val TAG = "SearchActivity"
        const val KEYWORDS = "keywords"
    }
    private lateinit var adapter: HotSearchAdapter
    private val stringList: MutableList<String> = ArrayList()
    private lateinit var binding: ActivitySearchBinding
    private val searchListener = object : HotSearchAdapter.OnHotSearchAdapterClickListen {
        override fun onHotSearchClick(position: Int) {

        }
    }

    override fun initData() {
        setBackBtn("#ffffff")
        setEditText("#ccffffff")
        setRightSearchButton()

        adapter = HotSearchAdapter(this)
        binding.rvHotsearch.layoutManager = LinearLayoutManager(this)
        binding.rvHotsearch.adapter = adapter
        adapter.listener = searchListener

    }

    override fun onResume() {
        super.onResume()

        if (SearchHistoryDaoOp.queryAll() != null) {

        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
    }
}