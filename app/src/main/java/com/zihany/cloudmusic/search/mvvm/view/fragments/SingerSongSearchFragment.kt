package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentSingerHotSongBinding
import com.zihany.cloudmusic.search.adapter.SongListAdapter
import com.zihany.cloudmusic.search.bean.SingerSongSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SingerViewModel

class SingerSongSearchFragment: BaseFragment<FragmentSingerHotSongBinding>(R.layout.fragment_singer_hot_song) {

    init {
        fragmentTitle = "热门单曲"
    }

    private lateinit var adapter: SongListAdapter
    private var hotSongList = ArrayList<SingerSongSearchBean.HotSongsBean>()
    private var songInfos = ArrayList<SongInfo>()
    private var type: String? = null
    private var singerId = -1L

    override fun initData() {
        adapter = SongListAdapter(context!!)
        binding.rvSingerSong.layoutManager = LinearLayoutManager(context)
        binding.rvSingerSong.adapter = adapter
        adapter.type = 2

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