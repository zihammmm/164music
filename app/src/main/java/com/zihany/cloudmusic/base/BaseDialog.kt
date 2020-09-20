package com.zihany.cloudmusic.base

import android.app.Dialog
import android.content.Context
import com.zihany.cloudmusic.R

class BaseDialog constructor(context: Context, themeResId: Int)
    : Dialog(context, themeResId) {

    constructor(context: Context): this(context, R.style.BaseDialog)

    //TODO
}