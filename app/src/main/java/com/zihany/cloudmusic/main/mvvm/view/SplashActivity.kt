package com.zihany.cloudmusic.main.mvvm.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.WindowManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.AUTH_TOKEN
import com.zihany.cloudmusic.base.PASSWORD
import com.zihany.cloudmusic.base.PHONE_NUMBER
import com.zihany.cloudmusic.util.*

class SplashActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SplashActivity"
    }

    var countDownTimer: CountDownTimer? = null

    //修改自动登录策略，使用保存好的phone和password进行自动登录，防止在调用需要登录的api时出错

    fun initData() {
        startCountDownTime()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        setContentView(R.layout.activity_splash)

        initData()
    }

    override fun onResume() {
        super.onResume()
        setStatusBarColor(Color.parseColor("#Db2C1F"))
    }

    private fun startCountDownTime() {
        countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
//                if (TextUtils.isEmpty(password)) {
                //TODO:先统一跳转到登录页面，再进行处理
                this@SplashActivity.startLoginActivity()
//                } else {
//                    ActivityStarter.instance.startMainActivity(this@SplashActivity)
//                }
                this@SplashActivity.finish()
            }
        }
        LogUtil.d(TAG, "startCountDownTime")
        countDownTimer?.start()
    }

    override fun finish() {
        super.finish()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }
}