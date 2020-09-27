package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.search.adapter.AlbumAdapter
import com.zihany.cloudmusic.search.bean.AlbumAdapterBean
import com.zihany.cloudmusic.search.bean.SingerAlbumSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel
import com.zihany.cloudmusic.util.ToastUtils

class SingerAlbumSearchFragment : BaseFragment<SingerViewModel>() {
    companion object {
        const val TAG = "SingerAlbumSearchFragment"
    }

    private lateinit var binding: FragmentRecyclerviewBinding
    private lateinit var adapter: AlbumAdapter
    private var hotAlbumList = ArrayList<SingerAlbumSearchBean.HotAlbumsBean>()
    private var adapterList = ArrayList<AlbumAdapterBean>()
    private var singerId = -1L

    private val listener = object : AlbumAdapter.OnAlbumClickListener {
        override fun onAlbumClick(position: Int) {
            ToastUtils.show(position.toString())
        }
    }

    init {
        fragmentTitle = "专辑"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecyclerviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        adapter = AlbumAdapter(context!!)
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter
        adapter.type = 2
        adapter.listener = listener

        if (singerId != -1L) {
            showDialog()
        }
    }
}