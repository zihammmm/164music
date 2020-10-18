package com.zihany.cloudmusic.util

import android.annotation.SuppressLint
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager

fun AppCompatActivity.setStatusBarColor(statusBarColor: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = statusBarColor
}

fun AppCompatActivity.setDefaultStatusBarFont(dark: Boolean) {
    window.decorView.apply {
        systemUiVisibility = if (dark) {
            systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}

fun AppCompatActivity.setStatusBarDarkFont(darkFont: Boolean) {
    if (setMIUIStatusBarDarkFont(darkFont)) {
        setDefaultStatusBarFont(darkFont)
    }
}

@SuppressLint("PrivateApi")
fun AppCompatActivity.setMIUIStatusBarDarkFont(dark: Boolean): Boolean {
    val clazz = window::class.java
    try {
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        val darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
        if (dark) {
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
        }else {
            extraFlagField.invoke(window, 0, darkModeFlag)
        }
        return true
    }catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}