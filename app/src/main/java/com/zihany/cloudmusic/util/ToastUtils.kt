package com.zihany.cloudmusic.util

import android.content.Context
import android.widget.Toast

class ToastUtils {
    companion object {
        fun show(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG)
                    .show()
        }
    }
}