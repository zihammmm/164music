package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentFeedBinding
import com.zihany.cloudmusic.main.mvvm.viewmodel.CloudVillageViewModel

class CloudVillageFragment: BaseFragment<CloudVillageViewModel>() {
    companion object {
        const val TAG = "CloudVillageFragment"
    }

    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter:

    init {
        fragmentTitle = "云村"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        binding.rvEvent.layoutManager = LinearLayoutManager(context)

    }
}