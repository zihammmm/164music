package com.zihany.Cloudmusic.song.bean

import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils

/**
 * 一行歌词
 */
class LrcEntry constructor(var time: Long, var text: String) : Comparable<LrcEntry> {

    companion object {
        val GRAVITY_CENTER = 0
        val GRAVITY_LEFT = 1
        val GRAVITY_RIGHT = 2
    }

    var secondText: String? = null

    var offset: Float = Float.MIN_VALUE
    var staticLayout: StaticLayout? = null

    fun init(paint: TextPaint, width: Int, gravity: Int) {
        val align: Layout.Alignment =
        when(gravity) {
            GRAVITY_LEFT -> Layout.Alignment.ALIGN_NORMAL
            GRAVITY_RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
            else -> Layout.Alignment.ALIGN_CENTER
        }
        staticLayout = StaticLayout.Builder.obtain(getShowText(), 0, getShowText().length, paint, width)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
        offset = Float.MIN_VALUE
    }

    fun getHeight(): Int {
        return staticLayout?.height ?: 0
    }

    private fun getShowText(): String {
        return if (!TextUtils.isEmpty(secondText)) {
            text + "\n" + secondText
        }else {
            text
        }
    }

    override fun compareTo(other: LrcEntry): Int {
        return (time - other.time).toInt()
    }
}