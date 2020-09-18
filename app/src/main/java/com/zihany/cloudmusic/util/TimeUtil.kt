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

        fun getDay(time: Long): String {
            val format = SimpleDateFormat("dd", Locale.getDefault())
            return format.format(time)
        }

        fun getMonth(time: Long): String {
            val format = SimpleDateFormat("MM", Locale.getDefault())
            return format.format(time)
        }

        fun getTimeStandard(time: Long): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return format.format(time)
        }

        fun isOver7am(time: Long): Boolean {
            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, 7)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            val sevenOClock = c.timeInMillis
            return sevenOClock < time
        }
    }
}