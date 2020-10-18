package com.zihany.cloudmusic.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.getTimeNoYMDH(): String {
    val format = SimpleDateFormat("mm:ss", Locale.getDefault())
    return format.format(this)
}

fun Long.getTimeStandardOnlyYMD(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(this)
}

fun Long.getDay(): String {
    val format = SimpleDateFormat("dd", Locale.getDefault())
    return format.format(this)
}

fun Long.getMonth(): String {
    val format = SimpleDateFormat("MM", Locale.getDefault())
    return format.format(this)
}

fun Long.getTimeStandard(): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(this)
}

fun Long.isOver7am(): Boolean {
    val c = Calendar.getInstance()
    c.set(Calendar.HOUR_OF_DAY, 7)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)
    val sevenOClock = c.timeInMillis
    return sevenOClock < this
}