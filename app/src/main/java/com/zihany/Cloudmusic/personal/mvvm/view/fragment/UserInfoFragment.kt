package com.zihany.Cloudmusic.personal.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zihany.Cloudmusic.base.BaseFragment
import com.zihany.Cloudmusic.databinding.FragmentUserInfoBinding
import com.zihany.Cloudmusic.personal.event.UserDetailEvent
import org.greenrobot.eventbus.EventBus

class UserInfoFragment: BaseFragment() {
    private val binding = FragmentUserInfoBinding()

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = FragmentUserInfoBinding.inflate(inflater, container, false)
        EventBus.getDefault().register(this)
        return view.root
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