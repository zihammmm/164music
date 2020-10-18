package com.zihany.cloudmusic.util

import android.content.Context
import android.util.TypedValue

class DensityUtil {
    companion object {
        fun px2dp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }

        fun px2sp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.scaledDensity
            return pxVal / scale
        }

        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.resources.displayMetrics).toInt()
        }

        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}

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