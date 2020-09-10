package com.zihany.Cloudmusic.util

import android.app.Activity
import android.content.Intent
import com.zihany.Cloudmusic.main.mvvm.view.MainActivity
import com.zihany.Cloudmusic.login.SelectLoginActivity

class ActivityStarter private constructor(){
    companion object {
        val instance: ActivityStarter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityStarter()
        }

    }

    fun startMainActivity(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun startLoginActivity(activity: Activity) {
        val intent = Intent(activity, SelectLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

}

