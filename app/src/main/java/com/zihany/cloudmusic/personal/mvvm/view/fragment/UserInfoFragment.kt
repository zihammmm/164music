package com.zihany.cloudmusic.personal.mvvm.view.fragment

import android.view.View
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentUserInfoBinding
import com.zihany.cloudmusic.personal.event.UserDetailEvent
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel
import org.greenrobot.eventbus.EventBus

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>(R.layout.fragment_user_info) {

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