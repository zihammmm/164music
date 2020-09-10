package com.zihany.Cloudmusic.main.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zihany.Cloudmusic.base.BaseViewModel
import com.zihany.Cloudmusic.main.bean.LogoutBean
import io.reactivex.rxjava3.core.Observable

class MainViewModel: BaseViewModel {
    val userName: MutableLiveData<String> by lazy {
        MutableLiveData<String>("unknown")
    }
}