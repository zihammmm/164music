package com.zihany.cloudmusic.login.mvvm.view

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.PHONE_NUMBER
import com.zihany.cloudmusic.databinding.ActivityLoginBinding
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginViewModel
import com.zihany.cloudmusic.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    companion object {
        const val TAG = "LoginActivity"
    }

    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun initData() {
        setBackBtn(getString(R.string.colorBlack))
        setLeftTitleText(getString(R.string.login_phone_number))
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(Color.parseColor(getString(R.string.colorWhite)))
        setStatusBarDarkFont(true)
    }

    override fun initView() {
        binding.run {
            lvm = loginViewModel
        }
    }

    override fun startObserve() {
        loginViewModel.apply {

            uiState.observe(this@LoginActivity, Observer {
                it.isSuccess?.let {
                    LogUtil.d(TAG, "login success")
                    ToastUtils.show("登录成功")
                    hideDialog()
                    this@LoginActivity.startMainActivity()
                }

                it.isError?.let {error ->
                    hideDialog()
                    ToastUtils.show("登录失败: $error")
                }
            })
        }
    }

    @ExperimentalCoroutinesApi
    override fun onClick(view: View) {
        if (view.isFastClick(1000)) {
            return
        }
    }
}