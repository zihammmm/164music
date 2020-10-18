package com.zihany.cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.zihany.cloudmusic.util.DensityUtil
import com.zihany.cloudmusic.util.dp2px

class PlayListRecommendViewPager constructor(
        private val mContext: Context, attrs: AttributeSet?
): ViewPager(mContext, attrs) {
    companion object {
        const val TAG = "PlayListRecommendViewPager"
    }
    private var lastY = 0f

    constructor(context: Context): this(context, null)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val currentItem = currentItem
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            if (currentItem == 0) {
                if (ev.y >= 0 && ev.y <= mContext.dp2px(165f)) {
                    lastY = ev.y
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

}