package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.main.bean.MainEventBean

class CloudVillageViewModel: BaseViewModel() {
    val bean = MutableLiveData<MainEventBean>()
    val getMainEventError = MutableLiveData<String>()

    override fun initData(context: Context) {
    }

    fun getMainEvent() {

    }
}