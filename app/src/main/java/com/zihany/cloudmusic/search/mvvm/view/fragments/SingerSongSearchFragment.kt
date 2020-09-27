package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentSingerHotSongBinding
import com.zihany.cloudmusic.search.adapter.SongListAdapter
import com.zihany.cloudmusic.search.bean.SingerSongSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel

class SingerSongSearchFragment: BaseFragment<SingerViewModel>() {

    init {
        fragmentTitle = "热门单曲"
    }

    private lateinit var binding: FragmentSingerHotSongBinding
    private lateinit var adapter: SongListAdapter
    private var hotSongList = ArrayList<SingerSongSearchBean.HotSongsBean>()
    private var songInfos = ArrayList<SongInfo>()
    private var type: String? = null
    private var singerId = -1L

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSingerHotSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        adapter = SongListAdapter(context!!)
        binding.rvSingerSong.layoutManager = LinearLayoutManager(context)
        binding.rvSingerSong.adapter = adapter
        adapter.type = 2

        if (singerId != -1L) {
            showDialog()
        }
    }
}