package com.zihany.cloudmusic.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemAlbumSearchBinding
import com.zihany.cloudmusic.search.bean.AlbumAdapterBean
import com.zihany.cloudmusic.util.TimeUtil
import com.zihany.cloudmusic.util.getTimeNoYMDH
import com.zihany.cloudmusic.util.getTimeStandardOnlyYMD

class AlbumAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, AlbumAdapterBean>(mContext) {

    var type = 0
    private var list: ArrayList<AlbumAdapterBean>? = null
    var keywords: String? = null
    var listener: OnAlbumClickListener? = null
    private lateinit var binding: ItemAlbumSearchBinding

    override fun notifyDataSetChanged(dataList: ArrayList<AlbumAdapterBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemAlbumSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(mContext, list!![position], keywords!!, type)
            holder.setListener(listener, position)
        }
    }

    override fun getItemCount() = list?.size ?: 0

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun setBean(context: Context, bean: AlbumAdapterBean, keywords: String, type: Int) {
            Glide.with(context)
                    .load(bean.albumCoverUrl)
                    .placeholder(R.drawable.shape_album)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivAlbum.ivCover)

            if (type == 1) {
                binding.tvSinger.visibility = View.VISIBLE
                if (bean.albumName!!.contains(keywords)) {
                    val start = bean.albumName!!.indexOf(keywords)
                    val end = start + keywords.length
                    val resString = bean.albumName
                    val style = SpannableStringBuilder(resString)
                            .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvName.text = style.toString()
                } else {
                    binding.tvName.text = bean.albumName
                }
                val artistName = bean.singer!!
                if (artistName.contains(keywords)) {
                    val start = artistName.indexOf(keywords)
                    val end = start + keywords.length
                    val style = SpannableStringBuilder(artistName)
                            .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvSinger.text = style.toString()
                } else {
                    binding.tvSinger.text = artistName
                }
                binding.tvDescription.text = bean.createTime.getTimeStandardOnlyYMD()
            } else if (type == 2) {
                binding.tvName.text = bean.albumName
                binding.tvDescription.text = "${bean.createTime.getTimeStandardOnlyYMD()} 歌曲 ${bean.songCount}"
            }
        }

        fun setListener(listener: OnAlbumClickListener?, position: Int) {
            binding.rlAlbum.setOnClickListener {
                listener?.onAlbumClick(position)
            }
        }
    }

    interface OnAlbumClickListener {
        fun onAlbumClick(position: Int)
    }

}