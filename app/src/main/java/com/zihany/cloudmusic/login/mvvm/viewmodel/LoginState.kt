package com.zihany.cloudmusic.login.mvvm.viewmodel

class LoginState<T>(
        val isSuccess: T? = null,
        val isError: String? = null
)