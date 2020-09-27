package com.zihany.cloudmusic.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import kotlin.math.max
import kotlin.math.min

class ScalableView constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : View(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap: Bitmap? = null
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var oldScale = 0f
    private var bigScale = 0f
    private var smallScale = 0f
    private var bigScaleMore = 1.5f
    private var currentScale = 0f
    private var isBig = false
    private var bigAnimator: ObjectAnimator? = null
    private var smallAnimator: ObjectAnimator? = null

    private val gestureListener = GestureListener()
    private val scaleListener = ScaleListener()

    private var gestureDetector = GestureDetectorCompat(context, gestureListener)
    private var scaleDetector = ScaleGestureDetector(context, scaleListener)

    private var scroller = OverScroller(context)
    private var runnable = SRunnable()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val scalingFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas?.translate(offsetX * scalingFraction, offsetX * scalingFraction)
        canvas?.scale(currentScale, currentScale, width / 2f, height / 2f)
        bitmap?.let {
            canvas?.drawBitmap(it, originalOffsetX, originalOffsetY, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (!scaleDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        } else {
            scaleDetector.onTouchEvent(event)
        }
    }

    fun setCurrentScale(currentScale: Float) {
        this.currentScale = currentScale
        invalidate()
    }

    fun getBigAnimator(): ObjectAnimator {
        if (bigAnimator == null) {
            bigAnimator = ObjectAnimator.ofFloat(this, "currentScale", currentScale, bigScale)
        }
        bigAnimator!!.setFloatValues(currentScale, bigScale)
        return bigAnimator!!
    }

    fun getSmallAnimator(): ObjectAnimator {
        if (smallAnimator != null) {
            smallAnimator = ObjectAnimator.ofFloat(this, "currentScale", currentScale, smallScale)
            smallAnimator?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    offsetX = 0f
                    offsetY = 0f
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationRepeat(animation: Animator?) {

                }

            })
        }
        smallAnimator?.setFloatValues(currentScale, smallScale)
        return smallAnimator!!
    }

    fun setImage(resource: Bitmap) {
        bitmap = resource

        originalOffsetX = (width - bitmap!!.width) / 2f
        originalOffsetY = (height - bitmap!!.height) / 2f

        if (bitmap!!.width / bitmap!!.height > width / height) {
            smallScale = width.toFloat() / bitmap!!.width
            bigScale = height.toFloat() / bitmap!!.height * bigScaleMore
        } else {
            smallScale = height.toFloat() / bitmap!!.height
            bigScale = width.toFloat() / bitmap!!.width * bigScaleMore
        }
        currentScale = smallScale
        invalidate()
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (e == null) {
                return super.onDoubleTap(e)
            }
            isBig = !isBig
            if (isBig && currentScale < bigScale) {
                offsetX = (e.x - width / 2) - ((e.x - width / 2) * bigScale / smallScale)
                offsetY = (e.y - height / 2) - ((e.y - height / 2) * bigScale / smallScale)
                notOutBound()
                getBigAnimator().start()
            } else {
                getSmallAnimator().start()
            }
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY
            notOutBound()
            invalidate()
            return false
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -(bitmap!!.width * bigScale - width).toInt() / 2,
                    (bitmap!!.width * bigScale - width).toInt() / 2,
                    -(bitmap!!.height * bigScale - height).toInt() / 2,
                    (bitmap!!.height * bigScale - height).toInt() / 2)
            postOnAnimation(runnable)
            return false
        }

    }

    private fun notOutBound() {
        offsetX = max(offsetX, -(bitmap!!.width * bigScale - width) / 2)
        offsetX = min(offsetX, (bitmap!!.width * bigScale - width) / 2)
        offsetY = max(offsetY, -(bitmap!!.height * bigScale - height) / 2)
        offsetY = min(offsetY, (bitmap!!.height * bigScale - height) / 2)
    }

    inner class ScaleListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            currentScale = oldScale * detector!!.scaleFactor
            currentScale = max(smallScale, currentScale)
            currentScale = min(currentScale, bigScale * 2)
            invalidate()
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            oldScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

    }

    inner class SRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(runnable)
            }
        }

    }
}