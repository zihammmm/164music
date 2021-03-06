package com.zihany.cloudmusic.dj.mvvm.view.fragments

import android.view.View
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRadioDetailBinding
import com.zihany.cloudmusic.dj.bean.DjDetailBean
import com.zihany.cloudmusic.dj.event.RidEvent
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.util.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RadioDetailFragment: BaseFragment<FragmentRadioDetailBinding>(R.layout.fragment_radio_detail) {

    init {
        fragmentTitle = "详情"
    }
    private var rid = 0L

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onGetRidEvent(event: RidEvent) {
        LogUtil.d(TAG, "onGetRigEvent: $event")
        rid = event.rid
    }

    override fun initData() {
        if (rid != 0L) {
            showDialog()
            //viewModel.getDjDetail(rid)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    fun onGetDjDetailSuccess(bean: DjDetailBean) {
        hideDialog()
        LogUtil.d(TAG, "onGetDjDetailSuccess: $bean")

    }

    fun onGetDjDetailFail(e: String) {
        hideDialog()
        LogUtil.e(TAG, "onGetDjDetailFail: $e")
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