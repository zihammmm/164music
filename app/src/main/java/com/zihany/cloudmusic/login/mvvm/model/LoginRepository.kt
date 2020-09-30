package com.zihany.cloudmusic.login.mvvm.model

import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.api.doError
import com.zihany.cloudmusic.api.doSuccess
import com.zihany.cloudmusic.base.BaseRepository
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginState
import com.zihany.cloudmusic.util.SharePreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class LoginRepository : BaseRepository() {

    suspend fun login(phone: String, password: String) = flow {
        if (phone.isBlank() || password.isBlank()) {
            return@flow
        }

        ApiEngine.instance.getApiService().login(phone, password).doSuccess { user ->
            emit(LoginState(isSuccess = user))
        }.doError { errorMsg ->
            emit(LoginState(isError = errorMsg))
        }
    }.onStart {
        emit(LoginState())
    }.flowOn(Dispatchers.IO)
            .catch {
                emit(LoginState(isError = it.message))
            }
}