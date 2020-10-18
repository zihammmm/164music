package com.zihany.cloudmusic.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.util.dp2px
import kotlinx.android.synthetic.main.activity_singer.view.*
import kotlin.math.max
import kotlin.math.min

class SingerAppBarBehavior constructor(
        context: Context, attrs: AttributeSet?
) : AppBarLayout.Behavior(context, attrs) {

    companion object {
        const val TAG = "SingerAppBarBehavior"
        const val MAX_SCALE = 0.5f
        val MAX_ZOOM_HEIGHT = App.getContext().dp2px(50f).toFloat()
    }

    private lateinit var imageView: ImageView
    private lateinit var imageViewCover: ImageView
    private lateinit var tvName: TextView
    private var appBarHeight = 0
    private var imageViewHeight = 0
    private lateinit var rlTop: RelativeLayout

    private var currentBottom = 0
    private var scaleValue = 0f
    private var totalDy = 0f
    private var isAnimate = false

    private var valueAnimator: ValueAnimator? = null

    override fun onLayoutChild(parent: CoordinatorLayout, abl: AppBarLayout, layoutDirection: Int): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        init(abl)
        return handled
    }

    private fun init(abl: AppBarLayout) {
        abl.clipChildren = false
        appBarHeight = abl.height
        imageView = abl.findViewById(R.id.iv_singer)
        imageViewCover = abl.findViewById(R.id.iv_singer_cover)
        rlTop = abl.findViewById(R.id.rl_top)
        tvName = abl.findViewById(R.id.tv_name)
        imageViewHeight = imageView.height
    }

    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        isAnimate = true
        return true
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (this::imageView.isInitialized && child.bottom >= appBarHeight && dy < 0 && type == ViewCompat.TYPE_TOUCH) {
            zoomHeaderImageView(child, dy)
        } else {
            if (this::imageView.isInitialized && child.bottom > appBarHeight && dy > 0 && type == ViewCompat.TYPE_TOUCH) {
                consumed[1] = dy
                zoomHeaderImageView(child, dy)
            } else {
                if (valueAnimator == null || !valueAnimator!!.isRunning) {
                    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
                }
            }
        }
    }

    private fun zoomHeaderImageView(abl: AppBarLayout, dy: Int) {
        totalDy += -dy
        totalDy = min(totalDy, MAX_ZOOM_HEIGHT)
        scaleValue = max(1f, 1f + totalDy / MAX_ZOOM_HEIGHT * MAX_SCALE)
        imageView.scaleX = scaleValue
        imageView.scaleY = scaleValue
        imageViewCover.scaleX = scaleValue
        imageViewCover.scaleY = scaleValue
        currentBottom = appBarHeight + (imageViewHeight / 2 * (scaleValue - 1)).toInt()
        rlTop.translationY = imageViewHeight / 2 * (scaleValue - 1)
        tvName.translationY = imageViewHeight / 2 * (scaleValue - 1)
        abl.bottom = currentBottom
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        if (velocityY > 100) {
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
        recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun recovery(abl: AppBarLayout) {
        if (totalDy > 0) {
            totalDy = 0f
            if (isAnimate) {
                valueAnimator = ValueAnimator.ofFloat(scaleValue, 1f).setDuration(220)
                valueAnimator?.addUpdateListener {
                    val value = it.animatedValue as Float
                    imageView.scaleX = value
                    imageView.scaleY = value
                    imageViewCover.scaleX = value
                    imageViewCover.scaleY = value
                    val bottom = (currentBottom - (currentBottom - appBarHeight) * it.animatedFraction)
                    rlTop.translationY = bottom - appBarHeight
                    tvName.translationY = bottom - appBarHeight
                    abl.bottom = bottom.toInt()
                }
                valueAnimator?.start()
            } else {
                imageView.scaleX = 1f
                imageView.scaleY = 1f
                imageViewCover.scaleX = 1f
                imageViewCover.scaleY = 1f
                rlTop.translationY = 0f
                tvName.translationY = 0f
                abl.bottom = appBarHeight
            }
        }
    }
}