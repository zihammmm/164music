package com.zihany.cloudmusic.personal.mvvm.view.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentNestedScrollViewBinding
import com.zihany.cloudmusic.personal.adapter.UserEventAdapter
import com.zihany.cloudmusic.personal.bean.UserEventBean
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel

class UserDynamicsFragment
    : BaseFragment<FragmentNestedScrollViewBinding>(R.layout.fragment_nested_scroll_view) {
    companion object {
        const val TAG = "UserDynamicsFragment"
    }

    private var eventsBeans = ArrayList<UserEventBean.EventsBean>()
    private var lastTime = 0L
    private var userId = 0L
    private var nickname = ""
    private lateinit var adapter: UserEventAdapter

    init {
        fragmentTitle = "动态"
    }

    override fun initData() {
        eventsBeans.clear()

        adapter = UserEventAdapter(context!!)
        binding.rv.layoutManager = LinearLayoutManager(context!!)
        binding.rv.adapter = adapter

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