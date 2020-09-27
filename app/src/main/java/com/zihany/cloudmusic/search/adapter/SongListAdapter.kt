package com.zihany.cloudmusic.search.adapter

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

class SongListAdapter constructor(
        private val context: Context
) : BaseAdapter<RecyclerView.ViewHolder, SongInfo>(context) {
    private lateinit var binding: ItemSonglistBinding
    var type = 0
    var keywords: String? = null
    private var list: ArrayList<SongInfo>? = null

    override fun notifyDataSetChanged(dataList: ArrayList<SongInfo>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSonglistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list == null || list!!.isEmpty()) {
            return
        }
        if (holder is ViewHolder) {
            val bean = list!![position]
            if (type == 3) {
                holder.setSongInfo(context, bean, keywords!!)
            } else {
                holder.setSongInfo(context, bean, position, type)
            }
            holder.setSongClick(bean, position)
        }
    }

    override fun getItemCount(): Int {
        return if (list == null) {
            0
        } else {
            if (type == 1) {
                20
            } else {
                list!!.size
            }
        }
    }

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        fun setSongInfo(context: Context, bean: SongInfo, position: Int, type: Int) {
            binding.tvSongname.text = bean.songName
            binding.tvSinger.text = bean.artist
            when (type) {
                1 -> {
                    binding.ivSongcover.visibility = View.VISIBLE
                    Glide.with(context)
                            .load(bean.songCover)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(binding.ivSongcover)
                }
                2 -> {
                    binding.ivSongnumber.apply {
                        visibility = View.VISIBLE
                        text = (position + 1).toString()
                    }
                }
                4 -> {
                    binding.ivPhone.visibility = View.VISIBLE
                    binding.ivSongdetail.visibility = View.VISIBLE
                }
            }
        }

        fun setSongInfo(context: Context, bean: SongInfo, keywords: String) {
            if (bean.songName.contains(keywords)) {
                val start = bean.songName.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.songName
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvSongname.text = style.toString()
            } else {
                binding.tvSongname.text = bean.songName
            }

            if (bean.artist.contains(keywords)) {
                val start = bean.artist.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.artist
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvSinger.text = style.toString()
            } else {
                binding.tvSinger.text = bean.artist
            }
        }

        fun setSongClick(songInfo: SongInfo, position: Int) {
            binding.rlSong.setOnClickListener {
                if (type == 3) {
                    SongPlayManager.instance.clickASong(songInfo)
                } else {
                    SongPlayManager.instance.clickPlayAll(list!!, position)
                }
                val intent = Intent(context, SongDetailActivity::class.java)
                intent.putExtra(SongActivity.SONG_INFO, songInfo)
                context.startActivity(intent)
                (context as Activity).overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent)
            }
        }
    }
}