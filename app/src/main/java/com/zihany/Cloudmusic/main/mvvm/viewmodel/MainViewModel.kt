package com.zihany.Cloudmusic.main.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lzx.starrysky.model.SongInfo
import com.zihany.Cloudmusic.base.BaseFragment
import com.zihany.Cloudmusic.base.BaseViewModel
import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.Cloudmusic.main.bean.LogoutBean
import com.zihany.Cloudmusic.main.mvvm.model.MainModel
import com.zihany.Cloudmusic.util.SharePreferenceUtil
import io.reactivex.rxjava3.core.Observable

class MainViewModel: BaseViewModel {
    var pagerAdapter: MultiFragmentPagerAdapter? = null
    val fragments: MutableList<BaseFragment> = ArrayList()
    lateinit var loginBean: LoginBean
    var songInfos: MutableList<SongInfo>? = null

    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>("unknown")
    }

    override fun initData() {
        val userLoginInfo = SharePreferenceUtil.getInstance()
    }

}