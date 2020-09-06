package com.zihany.Cloudmusic.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.zihany.Cloudmusic.databinding.LayoutBottomSongplayControlBinding
import com.zihany.Cloudmusic.song.SongActivity
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus

class BottomSongPlayBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : RelativeLayout(context, attrs, defStyleAttr) {
    companion object {
        private val TAG = "BottomSongPlayBar"
    }

    private var mContext: Context? = context
    private var rlSongController: RelativeLayout? = null
    private var ivCover: CircleImageView? = null
    private var ivPlay: ImageView? = null
    private var ivController: ImageView? = null
    private var tvSongName: TextView? = null
    private var tvSongSinger: TextView? = null
    private var llSongInfo: LinearLayout? = null

    private lateinit var binding: LayoutBottomSongplayControlBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        EventBus.getDefault().register(this)
        initView()
        initListener()
        initSongInfo()
    }

    private fun initView() {
        binding = LayoutBottomSongplayControlBinding.inflate()
    }

    private fun initListener() {
        ivCover!!.setOnClickListener { v ->
            val intent = Intent(mContext, SongActivity::class.java)
            intent.putExtra()
        }
    }

    private fun initSongInfo() {

    }
}