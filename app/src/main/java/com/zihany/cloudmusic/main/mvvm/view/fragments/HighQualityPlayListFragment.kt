package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel

class HighQualityPlayListFragment constructor(private var type: String): BaseFragment<WowViewModel>() {
    companion object {
        const val TAG = "HighQualityPlayListFrag"
        const val INIT_LOAD_LIMIT = 21
    }

    init {
        fragmentTitle = type
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

    }

    override fun initVariables(bundle: Bundle) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}