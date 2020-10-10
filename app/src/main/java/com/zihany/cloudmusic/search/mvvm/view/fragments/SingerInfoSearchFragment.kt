package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.LayoutSingerInfoBinding
import com.zihany.cloudmusic.search.adapter.SimiSingerAdapter
import com.zihany.cloudmusic.search.bean.SimiSingerBean
import com.zihany.cloudmusic.search.bean.SingerDescriptionBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel
import com.zihany.cloudmusic.util.ToastUtils

class SingerInfoSearchFragment
    : BaseFragment<LayoutSingerInfoBinding>(R.layout.layout_singer_info) {
    companion object {
        const val TAG = "SingerInfoSearchFragment"
    }

    private var singerId = -1L
    private lateinit var adapter: SimiSingerAdapter
    private var simiList = ArrayList<SimiSingerBean.ArtistsBean>()
    private var descBean: SingerDescriptionBean ? = null

    private val listener = object : SimiSingerAdapter.OnSimiSingerClickListener {
        override fun onSimiClick(position: Int) {
            ToastUtils.show(position.toString())
        }
    }

    init {
        fragmentTitle = "歌手信息"
    }

    override fun initData() {
        adapter = SimiSingerAdapter(context!!)
        adapter.listener = listener
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rv.adapter = adapter

        if (singerId != -1L) {
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