package com.zihany.cloudmusic.util

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.zihany.cloudmusic.main.mvvm.view.MainActivity
import com.zihany.cloudmusic.login.mvvm.view.SelectLoginActivity

class ActivityStarter private constructor(){
    companion object {
        val instance: ActivityStarter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityStarter()
        }
    }

    fun startMainActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun startLoginActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, SelectLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

}

