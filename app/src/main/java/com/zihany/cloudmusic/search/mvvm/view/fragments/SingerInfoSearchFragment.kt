package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.LayoutSingerInfoBinding
import com.zihany.cloudmusic.search.adapter.SimiSingerAdapter
import com.zihany.cloudmusic.search.bean.SimiSingerBean
import com.zihany.cloudmusic.search.bean.SingerDescriptionBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel
import com.zihany.cloudmusic.util.ToastUtils

class SingerInfoSearchFragment: BaseFragment<SingerViewModel>() {
    companion object {
        const val TAG = "SingerInfoSearchFragment"
    }

    private var singerId = -1L
    private lateinit var adapter: SimiSingerAdapter
    private var simiList = ArrayList<SimiSingerBean.ArtistsBean>()
    private var descBean: SingerDescriptionBean ? = null

    private lateinit var binding: LayoutSingerInfoBinding

    private val listener = object : SimiSingerAdapter.OnSimiSingerClickListener {
        override fun onSimiClick(position: Int) {
            ToastUtils.show(position.toString())
        }
    }

    init {
        fragmentTitle = "歌手信息"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutSingerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

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
}