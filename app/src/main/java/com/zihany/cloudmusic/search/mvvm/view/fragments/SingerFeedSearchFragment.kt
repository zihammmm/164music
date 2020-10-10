package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.search.adapter.FeedAdapter
import com.zihany.cloudmusic.search.bean.FeedSearchBean
import com.zihany.cloudmusic.search.bean.MvBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel

class SingerFeedSearchFragment : BaseFragment<FragmentRecyclerviewBinding>(R.layout.fragment_recyclerview) {
    companion object {
        const val TAG = "SingerFeedSearchFragment"
    }

    private val searchType = 1014
    private var keywords: String? = null
    private var type: String? = null
    private var videoList = ArrayList<FeedSearchBean.ResultBean.VideosBean>()
    private lateinit var adapter: FeedAdapter
    private var mvList = ArrayList<MvBean>()
    private var singerName: String? = null

    init {
        fragmentTitle = "相关视频"
    }

    override fun initData() {
        adapter = FeedAdapter(context!!)
        adapter.type = 2
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        singerName?.let {
            showDialog()
        }
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }
}