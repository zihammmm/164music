package com.zihany.lib_toast.helper

import androidx.appcompat.app.AppCompatActivity
import android.app.Application
import android.os.Bundle

class WindowHelper constructor(private val toast: ToastHelper)
    : Application.ActivityLifecycleCallbacks{

    companion object {
        fun register(toast: ToastHelper, application: Application): WindowHelper {
            val window = WindowHelper(toast)
            application.registerActivityLifecycleCallbacks(window)
            return window
        }
    }

    var topActivity: AppCompatActivity? = null

    override fun onActivityPaused(p0: AppCompatActivity) {
        if (toast.show) {
            toast.cancel()
        }
    }

    override fun onActivityStarted(p0: AppCompatActivity) {
        topActivity = p0
    }

    override fun onActivityDestroyed(p0: AppCompatActivity) {
        if (topActivity == p0) {
            topActivity = null
        }
    }

    override fun onActivitySaveInstanceState(p0: AppCompatActivity, p1: Bundle) {

    }

    override fun onActivityStopped(p0: AppCompatActivity) {

    }

    override fun onActivityCreated(p0: AppCompatActivity, p1: Bundle?) {
        topActivity = p0
    }

    override fun onActivityResumed(p0: AppCompatActivity) {
        topActivity = p0
    }
}