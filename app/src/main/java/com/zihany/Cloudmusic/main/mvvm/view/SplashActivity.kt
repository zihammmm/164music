package com.zihany.Cloudmusic.main.mvvm.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import com.zihany.Cloudmusic.R
import com.zihany.Cloudmusic.util.ActivityStarter
import com.zihany.Cloudmusic.util.ScreenUtils
import com.zihany.Cloudmusic.util.SharePreferenceUtil

class SplashActivity : AppCompatActivity() {
    companion object {
        private val TAG = "SplashActivity"
    }
    var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
                val authToken = SharePreferenceUtil.getInstance(this@SplashActivity).getAuthToken("")
                if (TextUtils.isEmpty(authToken)) {
                    ActivityStarter.instance.startLoginActivity(this@SplashActivity)
                }else {
                    ActivityStarter.instance.startMainActivity(this@SplashActivity)
                }
                this@SplashActivity.finish()
            }
        }
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