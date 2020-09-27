package com.zihany.cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.databinding.LayoutMusicDrawerItemBinding

class MusicDrawerItemView constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
): RelativeLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    private lateinit var binding: LayoutMusicDrawerItemBinding

    init {

    }

    private fun initView(context: Context) {
        binding = LayoutMusicDrawerItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initTypeArray(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MusicDrawerItemView)
        ta?.let {
            val drawable = it.getDrawable(R.styleable.MusicDrawerItemView_item_icon)
            binding.ivItemIcon.setImageDrawable(drawable)

            val text = it.getString(R.styleable.MusicDrawerItemView_item_name)
            binding.ivItemName.text = text

            val isShowGap = it.getBoolean(R.styleable.MusicDrawerItemView_is_show_gap, false)
            if (isShowGap) {
                binding.tvGap.visibility = VISIBLE
            }
            it.recycle()
        }

    }

    fun setText(text: String) {
        binding.ivItemName.text = text
    }
}