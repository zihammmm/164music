package com.zihany.cloudmusic.util

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.zihany.cloudmusic.main.mvvm.view.MainActivity
import com.zihany.cloudmusic.login.mvvm.view.SelectLoginActivity

fun AppCompatActivity.startMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun AppCompatActivity.startLoginActivity() {
    val intent = Intent(this, SelectLoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

