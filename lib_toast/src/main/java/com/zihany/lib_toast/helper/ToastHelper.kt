package com.zihany.lib_toast.helper

import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import android.widget.Toast

class ToastHelper constructor(private val toast: Toast, application: Application )
    : Handler(Looper.getMainLooper()) {
    private val windowHelper: WindowHelper
    var show = false
    private var packageName = ""

    init {
        packageName = application.packageName
        windowHelper = WindowHelper.register(this, application)
    }

    override fun handleMessage(msg: Message) {
        cancel()
    }

    fun show() {
        if (!show) {
            val params = WindowManager.LayoutParams()
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.format = PixelFormat.TRANSLUCENT
            params.windowAnimations = android.R.style.Animation_Toast
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            params.packageName = packageName
            params.gravity = toast.gravity
            params.x = toast.xOffset
            params.y = toast.yOffset
            params.verticalMargin = toast.verticalMargin
            params.horizontalMargin = toast.horizontalMargin

            windowHelper.topActivity?.let {
                if (!it.isFinishing) {
                    val windowsManager = it.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    windowsManager.addView(toast.view, params)
                }
            }
        }
    }

    fun cancel() {
        removeMessages(hashCode())
        if (show) {

        }
    }
}