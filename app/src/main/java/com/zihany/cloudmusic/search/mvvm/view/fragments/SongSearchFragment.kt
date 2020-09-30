package com.zihany.cloudmusic.search.mvvm.view.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentSearchSongBinding
import com.zihany.cloudmusic.main.adapter.SongListAdapter
import com.zihany.cloudmusic.search.bean.SongSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class SongSearchFragment constructor(
        private val type: String?
) : BaseFragment<SearchViewModel>() {

    constructor() : this(null)

    private lateinit var binding: FragmentSearchSongBinding

    private var keywords: String? = null
    private var searchType = 1
    private var adapter: SongListAdapter? = null
    private var resultBeans = ArrayList<SongSearchBean.ResultBean.SongsBean>()
    private var needRefresh = false
    private var songInfos = ArrayList<SongInfo>()

    init {
        fragmentTitle = type
    }

    override fun initData() {
        resultBeans.clear()

        adapter = SongListAdapter(context!!)
        adapter?.type = 3
        adapter?.keywords = keywords
        binding.rvSongSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSongSearch.adapter = adapter

        keywords?.let {
            showDialog()
        }
    }

    override fun onVisible() {
        super.onVisible()
        if (needRefresh) {
            needRefresh = false
            showDialog()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}