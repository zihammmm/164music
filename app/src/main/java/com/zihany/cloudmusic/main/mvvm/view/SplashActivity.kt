package com.zihany.cloudmusic.main.mvvm.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.util.ActivityStarter
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ScreenUtils
import com.zihany.cloudmusic.util.SharePreferenceUtil

class SplashActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SplashActivity"
    }
    var countDownTimer: CountDownTimer? = null

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
        ScreenUtils.setStatusBarColor(this, Color.parseColor("#Db2C1F"))
    }

    fun startCountDownTime() {
        countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val authToken = SharePreferenceUtil.instance.getAuthToken("")
                /*if (TextUtils.isEmpty(authToken)) {
                    ActivityStarter.instance.startLoginActivity(this@SplashActivity)
                }else {*/
                    ActivityStarter.instance.startMainActivity(this@SplashActivity)
                /*}*/
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