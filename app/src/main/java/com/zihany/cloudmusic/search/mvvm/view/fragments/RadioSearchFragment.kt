package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.search.adapter.RadioSearchAdapter
import com.zihany.cloudmusic.search.bean.RadioSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel
import com.zihany.cloudmusic.util.ToastUtils

class RadioSearchFragment constructor(
        private val type: String?
): BaseFragment<FragmentRecyclerviewBinding>(R.layout.fragment_recyclerview) {

    companion object {
        const val TAG = "RadioSearchFragment"
    }

    private val searchType = 1009
    var keywords: String? = null
    private var needRefresh = false
    private var djList = ArrayList<RadioSearchBean.ResultBean.DjRadiosBean>()
    private lateinit var adapter: RadioSearchAdapter
    private val listener = object : RadioSearchAdapter.OnRadioClickListener {
        override fun onRadioClick(position: Int) {
            ToastUtils.show("点击了:${position}")
        }
    }

    init {
        fragmentTitle = type
    }

    override fun initData() {
        adapter = RadioSearchAdapter(context!!)
        adapter.keywords = keywords
        adapter.listener = listener
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        keywords?.let{
            showDialog()
        }
    }

    override fun onVisible() {
        super.onVisible()
        if (needRefresh) {
            needRefresh = false
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