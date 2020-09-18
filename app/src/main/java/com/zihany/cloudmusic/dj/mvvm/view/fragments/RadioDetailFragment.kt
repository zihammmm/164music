package com.zihany.cloudmusic.dj.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRadioDetailBinding
import com.zihany.cloudmusic.dj.bean.DjDetailBean
import com.zihany.cloudmusic.dj.event.RidEvent
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RadioDetailFragment: BaseFragment<DjViewModel>() {

    init {
        fragmentTitle = "详情"
    }
    private var rid = 0L
    private lateinit var binding: FragmentRadioDetailBinding

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onGetRidEvent(event: RidEvent) {
        LogUtil.d(TAG, "onGetRigEvent: $event")
        rid = event.rid
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[DjViewModel::class.java]
        binding = FragmentRadioDetailBinding.inflate(inflater, container, false)
        EventBus.getDefault().register(this)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        if (rid != 0L) {
            showDialog()
            viewModel.getDjDetail(rid)
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
}