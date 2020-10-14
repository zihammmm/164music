package com.zihany.lib_toast.helper

import android.app.Activity
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

    var topActivity: Activity? = null

    override fun onActivityPaused(p0: Activity) {
        if (toast.show) {
            toast.cancel()
        }
    }

    override fun onActivityStarted(p0: Activity) {
        topActivity = p0
    }

    override fun onActivityDestroyed(p0: Activity) {
        if (topActivity == p0) {
            topActivity = null
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        topActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        topActivity = p0
    }
}