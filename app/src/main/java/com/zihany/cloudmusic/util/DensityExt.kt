package com.zihany.cloudmusic.util

import android.content.Context
import android.util.TypedValue

fun Context.px2dp(pxVal: Float): Float {
    val scale = resources.displayMetrics.density
    return pxVal / scale
}

fun Context.px2sp(pxVal: Float): Float {
    val scale = resources.displayMetrics.scaledDensity
    return pxVal / scale
}

fun Context.sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, resources.displayMetrics).toInt()
}

fun Context.dp2px(dpVal: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpVal * scale + 0.5f).toInt()
}