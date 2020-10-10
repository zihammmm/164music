package com.zihany.cloudmusic.dj.mvvm.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityRadioCatBinding
import com.zihany.cloudmusic.dj.adapter.DjCateAdapter
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel

class RadioCatActivity: BaseActivity() {
    companion object {
        const val TAG = "RadioCatActivity"
    }
    private lateinit var adapter: DjCateAdapter
    private lateinit var binding: ActivityRadioCatBinding

    override fun initData() {
        setBackBtn("#ffffff")
        setLeftTitleText("电台分类", "#ffffff")

        adapter = DjCateAdapter(this)
        binding.rvCate.layoutManager = GridLayoutManager(this, 2)
        binding.rvCate.adapter = adapter

        showDialog()
//        viewModel.getDjCateList()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[DjViewModel::class.java]
    }

}