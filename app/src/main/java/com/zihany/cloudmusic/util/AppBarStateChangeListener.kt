package com.zihany.cloudmusic.util

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener: AppBarLayout.OnOffsetChangedListener {
    companion object {
        const val TAG = "AppBarStateChangeListener"
    }

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        onOffsetChanged(appBarLayout)
        if (verticalOffset == 0) {
            if (currentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED)
            }
            currentState = State.EXPANDED
        }else if (abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            if (currentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED)
            }
            currentState = State.COLLAPSED
        }else {
            if (currentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE)
            }
            currentState = State.IDLE
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State)

    abstract fun onOffsetChanged(appBarLayout: AppBarLayout?)
}