package com.zihany.cloudmusic.login.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.api.ApiService
import com.zihany.cloudmusic.api.doError
import com.zihany.cloudmusic.api.doSuccess
import com.zihany.cloudmusic.base.BaseRepository
import com.zihany.cloudmusic.base.USER_INFO
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginState
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class LoginRepository : BaseRepository() {
    companion object {
        const val TAG = "LoginRepository"
    }

    fun login(phone: String, password: String) = ApiEngine.instance.getApiService().login(phone, password)

}