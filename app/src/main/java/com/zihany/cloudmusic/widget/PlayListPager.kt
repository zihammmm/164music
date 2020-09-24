package com.zihany.cloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PlayListPager constructor(
        private val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int
) : ViewGroup(mContext, attrs, defStyleAttr) {
    companion object {
        val MIN_SCALE = 0.75f
        val MIN_ALPHA = 0.5f
        val MIN_SLOP_DISTANCE = 5
    }

    private var downX = 0
    private var downY = 0
    private var totalOffsetX = 0f
    private var offsetAnimator: ValueAnimator? = null
    private var offsetPercent = 0f
    private var isDragged = false
    private var isReordered = false
    private var lastX = 0f
    private var lastY = 0f
    private var clickListener: OnPlayListClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (heightMode == MeasureSpec.EXACTLY) {

        }
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        var totalWidth = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            totalWidth = widthSize
        } else {
            for (i: Int in 0..childCount) {
                val lp = getChildAt(i).layoutParams as LayoutParams
                totalWidth += getChildAt(i).measuredWidth + lp.leftMargin + lp.rightMargin
            }
        }
        return totalWidth
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var maxHeight = 0

        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (heightMode == MeasureSpec.EXACTLY) {
            maxHeight = heightSize
        } else {
            for (i: Int in 0..childCount) {
                val lp = getChildAt(i).layoutParams as LayoutParams
                maxHeight = max(maxHeight, getChildAt(i).measuredHeight + lp.topMargin + lp.bottomMargin)
            }
        }
        return maxHeight
    }

    class LayoutParams : MarginLayoutParams {
        constructor(width: Int, height: Int) : super(width, height)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
        constructor(source: ViewGroup.LayoutParams) : super(source)

        var scale = 0f
        var alpha = 0f
        var from = 0
        var to = 0
        var index = 0
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i: Int in 0..childCount) {
            val baseLineX = calBaseLine(i)
            TODO("0924")
        }
    }

    private fun calBaseLine(index: Int): Int {
        val baseLineLeft = width / 4f
        val baseLineCenter = width / 2f
        val baseLineRight = width - baseLineLeft

        val lp = getChildAt(index).layoutParams as LayoutParams

        return when (lp.from) {
            0 -> {
                when (lp.to) {
                    1 -> baseLineLeft + (baseLineRight - baseLineLeft) * -offsetPercent
                    2 -> baseLineLeft + (baseLineCenter - baseLineLeft) * offsetPercent
                    else -> baseLineLeft
                }
            }
            1 -> {
                when (lp.to) {
                    0 -> baseLineRight - (baseLineRight - baseLineLeft) * offsetPercent
                    2 -> baseLineRight + (baseLineRight - baseLineCenter) * offsetPercent
                    else -> {
                        baseLineRight
                    }
                }
            }
            else -> {
                when (lp.to) {
                    1 -> baseLineCenter + (baseLineRight - baseLineCenter) * offsetPercent
                    0 -> baseLineCenter + (baseLineCenter - baseLineLeft) * offsetPercent
                    else -> {
                        baseLineCenter
                    }
                }
            }
        }.toInt()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        return LayoutParams(mContext, attrs)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        val lp = if (params is LayoutParams) {
            params
        } else {
            LayoutParams(params) as LayoutParams
        }
        if (childCount < 2) {
            lp.alpha = MIN_ALPHA
            lp.scale = MIN_SCALE
        } else {
            lp.alpha = 1f
            lp.scale = 1f
        }
        super.addView(child, index, params)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        isDragged = false
        if (ev == null) {
            return super.onInterceptTouchEvent(ev)
        }
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                downY = y
                lastX = x.toFloat()
                lastY = y.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = (x - lastX).toInt()
                val offsetY = (y - lastY).toInt()
                if (abs(offsetX) > MIN_SLOP_DISTANCE && abs(offsetY) > MIN_SLOP_DISTANCE) {
                    lastX = x.toFloat()
                    lastY = y.toFloat()
                    isDragged = true
                }
            }
            MotionEvent.ACTION_UP -> {
                handleActionUp(x, y)
                isDragged = false
            }
        }
        lastX = ev.x
        lastY = ev.y
        return true
    }

    fun moveItem() {
        offsetPercent = totalOffsetX / width
        setViewFromAndTo()
        changeViewLevel()
        changeAlphaAndScale()
        requestLayout()
    }

    private fun changeAlphaAndScale() {
        for (i: Int in 0..childCount) {
            val lp = getChildAt(i).layoutParams as LayoutParams
            when (lp.from) {
                0 -> {
                    if (lp.to == 2) {
                        lp.alpha = MIN_ALPHA + (1f - MIN_ALPHA) * offsetPercent
                        lp.scale = MIN_SCALE + (1f - MIN_SCALE) * offsetPercent
                    } else if (lp.to == 1) {
                        exchangeOrder(i, 0)
                    }
                }
                1 -> {
                    if (lp.to == 0) {
                        exchangeOrder(i, 0)
                    } else if (lp.to == 2) {
                        lp.alpha = MIN_ALPHA + (1f - MIN_ALPHA) * abs(offsetPercent)
                        lp.scale = MIN_SCALE + (1f - MIN_SCALE) * abs(offsetPercent)
                    }
                }
                2 -> {
                    lp.alpha = 1f - (1f - MIN_ALPHA) * abs(offsetPercent)
                    lp.scale = 1f - (1f - MIN_SCALE) * abs(offsetPercent)
                }
            }
        }
    }

    private fun setViewFromAndTo() {
        if (abs(offsetPercent) >= 1f) {
            isReordered = false
            for (i: Int in 0..childCount) {
                val lp = getChildAt(i).layoutParams as LayoutParams
                lp.from = lp.to
            }
            totalOffsetX %= width
            offsetPercent %= 1f
        } else {
            for (i: Int in 0..childCount) {
                val lp = getChildAt(i).layoutParams as LayoutParams
                lp.to =
                        when (lp.from) {
                            0 -> if (offsetPercent > 0) {
                                2
                            } else {
                                1
                            }
                            1 -> if (offsetPercent > 0) {
                                0
                            } else {
                                2
                            }
                            2 -> if (offsetPercent > 0) {
                                1
                            } else {
                                0
                            }
                            else -> 0
                        }
            }
        }
    }

    private fun changeViewLevel() {
        if (abs(offsetPercent) >= 0.5f) {
            if (!isReordered) {
                exchangeOrder(1, 2)
                isReordered = true
            }
        } else {
            if (isReordered) {
                exchangeOrder(1, 2)
                isReordered = false
            }
        }
    }

    private fun exchangeOrder(fromIndex: Int, toIndex: Int) {
        if (fromIndex == toIndex) {
            return
        }
        val fromChild = getChildAt(fromIndex)
        val toChild = getChildAt(toIndex)

        detachViewFromParent(fromIndex)
        detachViewFromParent(toIndex)

        attachViewToParent(fromChild, min(toIndex, childCount), fromChild.layoutParams)
        attachViewToParent(toChild, min(fromIndex, childCount), toChild.layoutParams)

        invalidate()
    }

    private fun setSelection(clickView: View) {
        val start = 0
        val lp = clickView.layoutParams as LayoutParams
        val end = if (lp.from == 0) {
            width
        } else if (lp.from == 1) {
            -width
        } else {
            0
        }
        startAnimator(start, end)
    }

    private fun initAnimator() {
        if (offsetAnimator?.isRunning!!) {
            offsetAnimator?.cancel()
        }
        val start = totalOffsetX.toInt()
        val end = if (offsetPercent >= 0.5f) {
            width
        } else {
            -width
        }
        startAnimator(start, end)
    }

    private fun startAnimator(start: Int, end: Int) {
        if (offsetAnimator == null) {
            offsetAnimator = ValueAnimator.ofInt(start, end)
            offsetAnimator!!.interpolator = LinearInterpolator()
            offsetAnimator!!.addUpdateListener {
                totalOffsetX = it.animatedValue as Float
                moveItem()
            }
        }
        offsetAnimator!!.setIntValues(start, end)
        offsetAnimator!!.start()
    }

    private fun isPointInView(view: View, points: FloatArray): Boolean {
        points[0] = points[0] - view.left
        points[1] = points[1] - view.top
        val matrix = view.matrix
        if (!matrix.isIdentity) {
            matrix.invert(matrix)
            matrix.mapPoints(points)
        }
        return points[0] >= 0 && points[1] >= 0 && points[0] < view.width && points[1] < view.height
    }

    private fun handleActionUp(x: Int, y: Int) {
        if (abs(x - downX) < MIN_SLOP_DISTANCE && abs(y - lastY) < MIN_SLOP_DISTANCE) {
            for (i: Int in childCount - 1..0) {
                val points = FloatArray(2)
                points[0] = x.toFloat()
                points[1] = y.toFloat()

                val clickView = getChildAt(i)
                if (isPointInView(clickView, points)) {
                    if (i != 2) {
                        setSelection(clickView)
                    } else {
                        val lp = clickView.layoutParams as LayoutParams
                        clickListener?.onPlayListClick(lp.index)
                    }
                    break
                }
            }
            return
        }
        initAnimator()
    }

    interface OnPlayListClickListener {
        fun onPlayListClick(position: Int)
    }
}


