package com.zihany.cloudmusic.dj.mvvm.view.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.main.adapter.SongListAdapter

class RadioProgramFragment
    : BaseFragment<FragmentRecyclerviewBinding>(R.layout.fragment_recyclerview) {
    companion object {
        const val TAG = "RadioProgramFragment"
    }
    private var adapter: SongListAdapter? = null
    private var rid = 0L

    init {
        fragmentTitle = "节目"
    }

    override fun initData() {
        binding.rv.layoutManager = LinearLayoutManager(context)
        adapter = SongListAdapter(context!!)
        adapter!!.type = 2
        binding.rv.adapter = adapter

        if (rid == 0L) {
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