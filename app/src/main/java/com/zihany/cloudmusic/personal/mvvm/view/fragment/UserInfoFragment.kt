package com.zihany.cloudmusic.personal.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentUserInfoBinding
import com.zihany.cloudmusic.personal.event.UserDetailEvent
import org.greenrobot.eventbus.EventBus

class UserInfoFragment: BaseFragment() {
    private lateinit var binding: FragmentUserInfoBinding

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        EventBus.getDefault().register(this)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun onUserDetailEvent(event: UserDetailEvent) {
        val bean = event.userDetailBean
        TODO("0910")
    }

    override fun initVariables(bundle: Bundle) {
    }

    override fun initData() {
    }
}