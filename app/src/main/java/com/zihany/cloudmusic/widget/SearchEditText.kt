package com.zihany.cloudmusic.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.zihany.cloudmusic.databinding.LayoutSearchEtBinding
import kotlin.math.max
import kotlin.math.min

class SearchEditText constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        RelativeLayout(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "SearchEditText"
    }

    private lateinit var binding: LayoutSearchEtBinding
    private var highlightColor = 0
    private var normalColor = 0

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        initView(context)
        initListener()
    }

    private fun initView(context: Context) {
        binding = LayoutSearchEtBinding.inflate(LayoutInflater.from(context), this, false)
    }

    private fun initListener() {
        binding.rlEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.et.requestFocus()
            }
        }

        binding.et.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.tvGap.setBackgroundColor(highlightColor)
            }    else {
                binding.tvGap.setBackgroundColor(normalColor)
            }
        }
    }

    fun getKeyWords() = binding.tvGap.text.toString().trim()

    fun setEditTextColor(color: String) {
        highlightColor = Color.parseColor(color)
        binding.tvGap.setTextColor(highlightColor)
        val a = min(255, max(0, (0.7 * 255).toInt())) shl 24
        val rgb = 0x00ffffff and Color.parseColor(color)
        normalColor = a + rgb
        binding.tvGap.setHintTextColor(normalColor)
    }

    fun setText(text: String) {
        binding.tvGap.text = text
    }
}