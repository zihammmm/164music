package com.zihany.cloudmusic.login.mvvm.view

import android.graphics.Color
import androidx.lifecycle.Observer
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityLoginBinding
import com.zihany.cloudmusic.login.mvvm.viewmodel.LoginViewModel
import com.zihany.cloudmusic.util.ActivityStarter
import com.zihany.cloudmusic.util.ScreenUtils
import com.zihany.cloudmusic.util.SharePreferenceUtil
import com.zihany.cloudmusic.util.ToastUtils
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
        ScreenUtils.setStatusBarColor(this, Color.parseColor(getString(R.string.colorWhite)))
        ScreenUtils.setStatusBarDarkFont(this, true)
    }

    override fun initView() {
        binding.run {
            lvm = loginViewModel
        }
    }

    override fun startObserve() {
        loginViewModel.apply {

            uiState.observe(this@LoginActivity, Observer {
                it.isSuccess?.let {loginBean ->
                    hideDialog()
                    SharePreferenceUtil.instance.saveUserInfo(loginBean, binding.etPhone.text.toString())
                    ActivityStarter.instance.startMainActivity(this@LoginActivity)
                }

                it.isError?.let {error ->
                    hideDialog()
                    ToastUtils.show(error)
                }
            })
        }
    }
}