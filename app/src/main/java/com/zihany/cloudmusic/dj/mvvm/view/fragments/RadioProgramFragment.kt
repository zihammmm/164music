package com.zihany.cloudmusic.dj.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.main.adapter.SongListAdapter

class RadioProgramFragment: BaseFragment<DjViewModel>() {
    companion object {
        const val TAG = "RadioProgramFragment"
    }
    private lateinit var binding: FragmentRecyclerviewBinding
    private var adapter: SongListAdapter? = null
    private var rid = 0L

    init {
        fragmentTitle = "节目"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

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
}