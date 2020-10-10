package com.zihany.cloudmusic.util

import android.util.Log
import com.zihany.cloudmusic.BuildConfig

class LogUtil {
    companion object {
        private val isDebug = BuildConfig.DEBUG

        fun i(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        fun v(tag: String, msg: String) {
            if (isDebug) {
                Log.v(tag, msg)
            }
        }

        fun d(tag: String, msg: String) {
            if (isDebug) {
                Log.d(tag, msg)
            }
        }

        fun w(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        fun e(tag: String, msg: String) {
            Log.e(tag, msg)
        }
    }
}