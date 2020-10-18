package com.zihany.cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.zihany.cloudmusic.util.dp2px

class PlayListRecommendScrollerView constructor(
        private val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int
): ScrollView(mContext, attrs, defStyleAttr) {
    companion object {
        const val TAG = "PlayListRecommendHorizontalScrollerView"
    }
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            if (ev.y >= 0 && ev.y <= mContext.dp2px(165f)) {
                return true
            }
        }
        return false
    }

}