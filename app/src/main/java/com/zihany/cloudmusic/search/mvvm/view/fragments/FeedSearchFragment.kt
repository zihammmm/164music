package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.search.adapter.FeedAdapter
import com.zihany.cloudmusic.search.bean.FeedSearchBean
import com.zihany.cloudmusic.search.bean.MvBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class FeedSearchFragment constructor(
        private var type: String?
): BaseFragment<SearchViewModel>() {

    constructor(): this(null)

    private var searchType = 1014
    private var keywords: String? = null
    private lateinit var adapter: FeedAdapter
    private var mvList = ArrayList<MvBean>()
    private var videoList = ArrayList<FeedSearchBean.ResultBean.VideosBean>()
    private lateinit var binding: FragmentRecyclerviewBinding
    private var needRefresh = false

    init {
        fragmentTitle = type
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        adapter = FeedAdapter(context!!)
        adapter.type = 1
        adapter.keywords = keywords?:""
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        keywords?.let{
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

}