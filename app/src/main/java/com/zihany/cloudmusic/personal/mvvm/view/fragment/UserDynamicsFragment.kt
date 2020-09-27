package com.zihany.cloudmusic.personal.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentNestedScrollViewBinding
import com.zihany.cloudmusic.databinding.FragmentUserInfoBinding
import com.zihany.cloudmusic.personal.adapter.UserEventAdapter
import com.zihany.cloudmusic.personal.bean.UserEventBean
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel

class UserDynamicsFragment: BaseFragment<PersonalViewModel>() {
    companion object {
        const val TAG = "UserDynamicsFragment"
    }

    private var eventsBeans = ArrayList<UserEventBean.EventsBean>()
    private var lastTime = 0L
    private var userId = 0L
    private var nickname = ""
    private lateinit var adapter: UserEventAdapter
    private lateinit var binding: FragmentNestedScrollViewBinding

    init {
        fragmentTitle = "动态"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNestedScrollViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        eventsBeans.clear()

        adapter = UserEventAdapter(context!!)
        binding.rv.layoutManager = LinearLayoutManager(context!!)
        binding.rv.adapter = adapter

    }
}