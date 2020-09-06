package com.zihany.Cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

class LyricView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
        View(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    init {
        initView()
        TODO("implement")
    }

    fun initView() {

    }

    fun updateTime(time: Long) {

    }


}