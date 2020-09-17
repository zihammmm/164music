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
import com.zihany.cloudmusic.databinding.ActivityRadioDetailBinding
import com.zihany.cloudmusic.util.DensityUtil
import kotlin.math.max
import kotlin.math.min

class RadioDetailAppBarBehavior constructor(context: Context, attrs: AttributeSet)
    : AppBarLayout.Behavior(context, attrs) {
    companion object {
        private var MAX_SCALE = 0.8f
        private val MAX_ZOOM_HEIGHT = DensityUtil.dp2px(App.getContext(), 80f).toFloat()
    }
    private var imageViewHeight = 0
    private var appBarHeight = 0
    private var currentBottom = 0
    private var scaleValue = 0f
    private var totalDy = 0f
    private var isAnimate = false
    private lateinit var valueAnimator: ValueAnimator

    private lateinit var imageView: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvSub: TextView
    private lateinit var tvInfo: TextView
    private lateinit var tvHasSub: TextView
    private lateinit var rlTop: RelativeLayout
    private lateinit var binding: ActivityRadioDetailBinding

    override fun onLayoutChild(parent: CoordinatorLayout, abl: AppBarLayout, layoutDirection: Int): Boolean {
        val handled = super.onLayoutChild(parent, abl, layoutDirection)
        init(abl)
        return handled
    }

    private fun init(abl: AppBarLayout) {
        abl.clipChildren = false
        appBarHeight = abl.height
        binding = ActivityRadioDetailBinding.bind(abl)
        imageViewHeight = binding.ivDjCover.height
    }

    override fun onStartNestedScroll(parent: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, nestedScrollAxes: Int, type: Int): Boolean {
        isAnimate = true
        return true
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (child.bottom >= appBarHeight && dy < 0 && type == ViewCompat.TYPE_TOUCH) {
            zoomHeaderImageView(child, dy)
        }else {
            if (child.bottom > appBarHeight && dy > 0 && type == ViewCompat.TYPE_TOUCH) {
                consumed[1] = dy
                zoomHeaderImageView(child, dy)
            }else {
                if (!this::valueAnimator.isInitialized || !valueAnimator.isRunning) {
                    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
                }
            }
        }
    }

    private fun zoomHeaderImageView(abl: AppBarLayout, dy: Int) {
        totalDy += -dy
        totalDy = min(totalDy, MAX_ZOOM_HEIGHT)
        scaleValue = max(1f, 1f + totalDy / MAX_ZOOM_HEIGHT + MAX_SCALE)
        binding.ivDjCover.scaleX = scaleValue
        binding.ivDjCover.scaleY = scaleValue
        val value = imageViewHeight / 2 * (scaleValue - 1)
        currentBottom = appBarHeight + value.toInt()
        binding.rlTop.translationY = value
        binding.tvName.translationY = value
        binding.tvInfo.translationY = value
        binding.tvSub.translationY = value
        binding.tvHasSub.translationY = value
        abl.bottom = currentBottom
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        if (velocityY > 100) {
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {

        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun recovery(abl: AppBarLayout) {
        if (totalDy > 0) {
            totalDy = 0f
            if (isAnimate) {
                valueAnimator = ValueAnimator.ofFloat(scaleValue, 1f).setDuration(220)
                valueAnimator.addUpdateListener {
                    val value = it.animatedValue as Float
                    binding.ivDjCover.scaleX = value
                    binding.ivDjCover.scaleY = value
                    val bottom = (currentBottom - (currentBottom - appBarHeight) * it.animatedFraction).toInt()
                    val newValue = (bottom - appBarHeight).toFloat()
                    binding.rlTop.translationY = newValue
                    binding.tvName.translationY = newValue
                    binding.tvInfo.translationY = newValue
                    binding.tvHasSub.translationY = newValue
                    binding.tvSub.translationY = newValue
                    abl.bottom = bottom
                }
                valueAnimator.start()
            }else {
                binding.ivDjCover.scaleX = 1f
                binding.ivDjCover.scaleY = 1f
                binding.rlTop.translationY = 0f
                binding.tvName.translationY = 0f
                binding.tvInfo.translationY = 0f
                binding.tvHasSub.translationY = 0f
                binding.tvSub.translationY = 0f
                abl.bottom = appBarHeight
            }
        }
    }

}