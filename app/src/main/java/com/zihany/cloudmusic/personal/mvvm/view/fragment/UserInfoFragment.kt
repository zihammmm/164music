package com.zihany.cloudmusic.personal.mvvm.view.fragment

import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentUserInfoBinding
import com.zihany.cloudmusic.personal.event.UserDetailEvent
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel
import org.greenrobot.eventbus.EventBus

class UserInfoFragment: BaseFragment<PersonalViewModel>() {
    private lateinit var binding: FragmentUserInfoBinding

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun onUserDetailEvent(event: UserDetailEvent) {
        val bean = event.userDetailBean
        TODO("0910")
    }

    override fun initData() {
    }
}