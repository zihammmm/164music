package com.zihany.Cloudmusic.util

import android.annotation.SuppressLint
import android.app.Activity
import android.view.WindowManager

class ScreenUtils {
    companion object {
        fun setStatusBarColor(activity: Activity, statusBarColor: Int) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = statusBarColor
        }

        fun setStatusBarDarkFont(activity: Activity, darkFont: Boolean) {

        }

        @SuppressLint("PrivateApi")
        private fun setMIUIStatusBarDarkFont(activity: Activity, dark: Boolean): Boolean{
            val window = activity.window
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
    }

}