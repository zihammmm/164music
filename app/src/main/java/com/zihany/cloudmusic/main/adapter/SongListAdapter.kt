package com.zihany.cloudmusic.main.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemSonglistBinding
import com.zihany.cloudmusic.manager.SongPlayManager
import com.zihany.cloudmusic.song.mvvm.view.SongActivity
import com.zihany.cloudmusic.song.mvvm.view.SongDetailActivity
import com.zihany.cloudmusic.util.LogUtil

class SongListAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, SongInfo>(mContext) {
    companion object {
        const val TAG = "SongListAdapter"
    }

    private lateinit var list: ArrayList<SongInfo>
    var type = 0
    var keywords: String? = null
    private lateinit var binding: ItemSonglistBinding

    override fun notifyDataSetChanged(dataList: ArrayList<SongInfo>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSonglistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (!this::list.isInitialized) {
            0
        } else if (type == 1) {
            20
        } else {
            list.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!this::list.isInitialized || list.isEmpty()) {
            return
        }
        if (holder is ViewHolder) {
            val bean = list[position]
            if (type == 3) {
                holder.setSongInfo(mContext, bean, keywords!!)
            } else {
                holder.setSongInfo(mContext, bean, position, type)
            }
            holder.setSongClick(bean, position)
        }
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun setSongInfo(context: Context, bean: SongInfo, position: Int, type: Int) {
            binding.tvSongname.text = bean.songName
            binding.tvSinger.text = bean.artist

            if (type == 1) {
                binding.ivSongcover.visibility = View.VISIBLE
                Glide.with(context)
                        .load(bean.songCover)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivSongcover)
            } else if (type == 2) {
                binding.ivSongnumber.visibility = View.VISIBLE
                binding.ivSongnumber.text = (position + 1).toString()
            } else if (type == 4) {
                binding.ivPhone.visibility = View.VISIBLE
                binding.ivSongdetail.visibility = View.GONE
            }
        }

        fun setSongInfo(context: Context, bean: SongInfo, keywords: String) {
            if (bean.songName.contains(keywords)) {
                val start = bean.songName.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.songName
                val style = SpannableStringBuilder(resString)
                style.setSpan(ForegroundColorSpan(Color.parseColor("#2196f3")),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvSongname.text = style
            } else {
                binding.tvSongname.text = bean.songName
            }

            if (bean.artist.contains(keywords)) {
                val start = bean.artist.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.artist
                val style = SpannableStringBuilder(resString)
                style.setSpan(ForegroundColorSpan(Color.parseColor("#2196f3")),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvSinger.text = style
            }else {
                binding.tvSinger.text = bean.artist
            }
        }

        fun setSongClick(songInfo: SongInfo, position: Int) {
            binding.rlSong.setOnClickListener {
                if (type == 3) {
                    SongPlayManager.instance.clickASong(songInfo)
                }else {
                    SongPlayManager.instance.clickPlayAll(list, position)
                }
                val intent = Intent(mContext, SongActivity::class.java)
                intent.putExtra(SongActivity.SONG_INFO, songInfo)
                mContext.startActivity(intent)
            }
            binding.ivSongdetail.setOnClickListener {
                val intent = Intent(mContext, SongDetailActivity::class.java)
                intent.putExtra(SongActivity.SONG_INFO, songInfo)
                mContext.startActivity(intent)
                (mContext as Activity).overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent)
            }
        }
    }
}