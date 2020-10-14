package com.zihany.cloudmusic.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.zihany.cloudmusic.R

class RoundRectView constructor(context: Context, attrs: AttributeSet?)
    : AppCompatImageView(context, attrs) {
    //TODO:修改
//    private var roundRatio = 16f
//    private var path = Path()
//
//    init {
//        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundRectView, 0, 0)
//        try {
//            roundRatio = typedArray.getFloat(R.styleable.RoundRectView_roundRatio, 16f)
//        }finally {
//            typedArray.recycle()
//        }
//        path.addRoundRect(RectF(0F, 0F, width.toFloat(), height.toFloat()),
//                roundRatio * width, roundRatio * height, Path.Direction.CW)
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        canvas?.save()
//        canvas?.clipPath(path)
//        super.onDraw(canvas)
//        canvas?.restore()
//    }
}