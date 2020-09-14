package com.zihany.cloudmusic.search.mvvm.view

import android.os.Bundle
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.search.adapter.HotSearchAdapter

class SearchActivity: BaseActivity() {
    companion object {
        val TAG = "SearchActivity"
        const val KEYWORDS = "keywords"
    }
    private lateinit var adapter: HotSearchAdapter
    private val stringList: MutableList<String> = ArrayList()

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}