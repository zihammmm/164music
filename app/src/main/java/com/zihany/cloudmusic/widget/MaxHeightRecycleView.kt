package com.zihany.cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.zihany.cloudmusic.R

class MaxHeightRecyclerView constructor(
        context: Context, attrs: AttributeSet?, defStyle: Int)
    : RecyclerView(context, attrs, defStyle) {
    companion object {
        const val TAG = "MaxHeightRecyclerView"
    }
    private var maxHeight = 0

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    init {
        initAttribute(context, attrs)
    }

    private fun initAttribute(context: Context, attrs: AttributeSet?) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        maxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, maxHeight)

        arr.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        var temp = heightSpec
        if (maxHeight > 0) {
            temp = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthSpec, temp)
    }
}