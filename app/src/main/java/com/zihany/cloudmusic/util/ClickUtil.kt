package com.zihany.cloudmusic.util

import android.util.SparseArray
import android.view.View

class ClickUtil {
    companion object {
        private val lastClickViewArray = SparseArray<Long>()

        fun isFastClick(minDelayTime: Int, view: View?): Boolean {
            val curClickTime = System.currentTimeMillis()
            val lastClickTime = lastClickViewArray[view!!.id, -1L]
            return (curClickTime - lastClickTime) <= minDelayTime
        }
    }
}