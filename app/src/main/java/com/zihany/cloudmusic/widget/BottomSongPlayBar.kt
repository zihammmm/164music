package com.zihany.cloudmusic.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.zihany.cloudmusic.databinding.LayoutBottomSongplayControlBinding
import com.zihany.cloudmusic.song.SongActivity
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus

class BottomSongPlayBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : RelativeLayout(context, attrs, defStyleAttr) {
    companion object {
        private val TAG = "BottomSongPlayBar"
    }

    private var mContext: Context? = context
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
        binding = LayoutBottomSongplayControlBinding.inflate(LayoutInflater.from(context), this, false)
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