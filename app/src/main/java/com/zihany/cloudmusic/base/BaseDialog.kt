package com.zihany.cloudmusic.base

import android.app.Dialog
import android.content.Context
import com.blankj.utilcode.util.ScreenUtils
import com.zihany.cloudmusic.R

open class BaseDialog constructor(context: Context, themeResId: Int)
    : Dialog(context, themeResId) {

    constructor(context: Context): this(context, R.style.BaseDialog)

    override fun show() {
        super.show()
        val w = window
        val p = w?.attributes
        p?.width = (ScreenUtils.getScreenWidth() * 0.8).toInt()
        w?.attributes = p
    }
}