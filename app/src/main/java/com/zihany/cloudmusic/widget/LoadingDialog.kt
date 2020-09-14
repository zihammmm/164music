package com.zihany.cloudmusic.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.view.animation.AnimationUtils
import androidx.annotation.NonNull
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.R

class LoadingDialog(@NonNull context: Context, private var msg: String) : Dialog(context) {
    companion object {
        val TAG = "LoadingDialog"
    }

    private val animation = AnimationUtils.loadAnimation(App.getContext(), R.anim.dialog_load_anim)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_load_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Handler(Looper.myLooper()!!).postDelayed({ setDialogCancelable(true) }, 3000)
    }

    private fun setDialogCancelable(cancelable: Boolean) {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
    }
}