package com.zihany.cloudmusic.login.mvvm.view

import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivitySelectLoginBinding
import com.zihany.cloudmusic.util.ClickUtil
import com.zihany.cloudmusic.util.ScreenUtils
import com.zihany.cloudmusic.util.ToastUtils
import kotlin.system.exitProcess

class SelectLoginActivity: BaseActivity() {
    companion object {
        const val TAG = "SelectLoginActivity"
    }
    private val binding by binding<ActivitySelectLoginBinding>(R.layout.activity_select_login)
    private var firstTime = 0L

    override fun onResume() {
        super.onResume()
        ScreenUtils.setStatusBarColor(this, Color.parseColor("#Db2C1F"))
    }

    override fun initData() {
        binding.apply {
            btnPhoneLogin.setOnClickListener {
                onClick(it)
            }
        }
    }

    override fun initView() {

    }

    override fun startObserve() {

    }

    override fun onClick(view: View) {
        if (ClickUtil.isFastClick(1000, view)) {
            return
        }
        when(view.id) {
            R.id.btn_phone_login -> {
                startActivity(LoginActivity::class.java, null, false)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp(2000)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exitApp(timeInterval: Long) {
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            ToastUtils.show(getString(R.string.press_exit_again))
            firstTime = System.currentTimeMillis()
        } else {
            finish()
            exitProcess(0)
        }
    }
}