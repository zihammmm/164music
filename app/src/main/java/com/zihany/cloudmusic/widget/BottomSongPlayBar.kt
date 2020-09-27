package com.zihany.cloudmusic.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.zihany.cloudmusic.databinding.LayoutBottomSongplayControlBinding
import com.zihany.cloudmusic.song.mvvm.view.SongActivity

class BottomSongPlayBar(private val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : RelativeLayout(mContext, attrs, defStyleAttr) {
    companion object {
        const val TAG = "BottomSongPlayBar"
    }

    private lateinit var binding: LayoutBottomSongplayControlBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        //TODO EventBus.getDefault().register(this)
        initView()
        initListener()
        initSongInfo()
    }

    private fun initView() {
        binding = LayoutBottomSongplayControlBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun initListener() {
        binding.ivCover.setOnClickListener { v ->
            val intent = Intent(mContext, SongActivity::class.java)
            //intent.putExtra()
        }
    }

    private fun initSongInfo() {

    }
}