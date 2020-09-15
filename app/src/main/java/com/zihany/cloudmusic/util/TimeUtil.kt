package com.zihany.cloudmusic.util

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {
    companion object {
        private val TAG = "TimeUtil"

        fun getTimeNoYMDH(time: Long): String {
            val format = SimpleDateFormat("mm:ss", Locale.getDefault())
            return format.format(time)
        }

        fun getTimeStandardOnlyYMD(time: Long): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return format.format(time)
        }
    }
}