package com.zihany.cloudmusic.login.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.login.mvvm.model.LoginRepository
import com.zihany.cloudmusic.util.SharePreferenceUtil
import kotlinx.coroutines.flow.collect

class LoginViewModel(val repository: LoginRepository) : BaseViewModel() {

    val phone = ObservableField<String>(SharePreferenceUtil.instance.getAccountNum() ?: "")
    val password = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginState<LoginBean>>()
    val uiState: LiveData<LoginState<LoginBean>>
        get() = _uiState

    fun loginDataChanged() {
        _uiState.value = LoginState()
    }

    fun login() {
        launchOnUI {
            repository.login(phone.get() ?: "", password.get() ?: "")
                    .collect {
                        _uiState.postValue(it)
                    }
        }
    }
}