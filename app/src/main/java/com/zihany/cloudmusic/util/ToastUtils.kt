package com.zihany.cloudmusic.util

import android.content.Context
import android.widget.Toast

class ToastUtils {
    companion object {
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
        }

        fun show(msg: String) {
            if (msg.length > 20) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG)
                        .show()
            }else {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}