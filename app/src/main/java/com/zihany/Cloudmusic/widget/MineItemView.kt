package com.zihany.Cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.zihany.Cloudmusic.R
import com.zihany.Cloudmusic.databinding.LayoutMusicDrawerItemBinding

class MineItemView constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
        RelativeLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    private val binding = LayoutMusicDrawerItemBinding()

    init {
        initTypeArray(context, attrs)
    }


    private fun initTypeArray(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MineItemView)
        ta?.let {
            val drawable = ta.getDrawable(R.styleable.MineItemView_icon)
            binding.ivItemIcon.setImageDrawable(drawable)

            val text = ta.getString(R.styleable.MineItemView_name)
            binding.ivItemName.text = text

            if (ta.getBoolean(R.styleable.MineItemView_show_gap, false)) {
                binding.tvGap.visibility = View.VISIBLE
            }

            ta.recycle()
        }
    }

}