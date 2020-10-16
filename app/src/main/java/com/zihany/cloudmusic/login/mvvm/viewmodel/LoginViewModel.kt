package com.zihany.cloudmusic.login.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.*
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.login.mvvm.model.LoginRepository
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }
    private var phoneDelegate by PreferenceUtils(PHONE_NUMBER, "")
    private var passwordDelegate by PreferenceUtils(PASSWORD, "")
    val phone = ObservableField<String>(phoneDelegate)
    val password = ObservableField<String>(passwordDelegate)
    private var userJson by PreferenceUtils(USER_INFO, "")
    private var authToken by PreferenceUtils(AUTH_TOKEN, "")

    private val _uiState = MutableLiveData<LoginState<LoginBean>>()
    val uiState: LiveData<LoginState<LoginBean>>
        get() = _uiState

    fun login() {
        repository.login(phone.get() ?: "", password.get() ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginBean> {
                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "onSubscribe")
                    }

                    override fun onNext(t: LoginBean?) {
                        LogUtil.d(TAG, "onNext: $t")
                        userJson = GsonUtil.toJson(t!!)
                        phoneDelegate = phone.get() ?: ""
                        passwordDelegate = password.get() ?: ""
                        authToken = t.bindings[1].tokenJsonStr
                        _uiState.postValue(LoginState(isSuccess = t))
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "onError: ${e?.message}")
                        _uiState.postValue(LoginState(isError = e?.message))
                    }

                    override fun onComplete() {
                        LogUtil.d(TAG, "onComplete")
                    }

                })
    }

}