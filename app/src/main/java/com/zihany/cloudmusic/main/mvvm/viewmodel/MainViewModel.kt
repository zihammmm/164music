package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson

import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.base.Result
import com.zihany.cloudmusic.base.USER_INFO
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.bean.LogoutBean
import com.zihany.cloudmusic.main.mvvm.model.LikeListRepository
import com.zihany.cloudmusic.main.mvvm.model.LogoutRepository
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
        private val likeListRepository: LikeListRepository,
        private val logoutRepository: LogoutRepository
) : BaseViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    var loginBean: MutableLiveData<LoginBean> = MutableLiveData()
    var logoutBean: MutableLiveData<LogoutBean> = MutableLiveData()
    var likeListBean: MutableLiveData<LikeListBean> = MutableLiveData()
    var getLikeListError: MutableLiveData<Throwable> = MutableLiveData()
    var logoutError: MutableLiveData<Throwable> = MutableLiveData()
    private val userLoginInfo by PreferenceUtils(USER_INFO, "")

    init {
        Log.d(TAG, userLoginInfo)
        loginBean.value = Gson().fromJson<LoginBean>(userLoginInfo, LoginBean::class.java)
    }

    fun getLikeList(uid: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = likeListRepository.getLikeList(uid)
//            if (result is Result.Success) {
//                likeListBean.value = result.data
//            } else if (result is Result.Error) {
//                getLikeListError.value = result.exception
//            }
//        }
    }

    fun logout() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = logoutRepository.logout()
//            if (result is Result.Success) {
//                logoutBean.value = result.data
//            } else if (result is Result.Error) {
//                logoutError.value = result.exception
//            }
//        }
    }

}