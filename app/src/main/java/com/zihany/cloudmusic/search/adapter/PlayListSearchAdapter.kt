package com.zihany.cloudmusic.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemPlaylistFragmentBinding
import com.zihany.cloudmusic.search.bean.PlayListSearchBean

class PlayListSearchAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, PlayListSearchBean.ResultBean.PlaylistsBean>(mContext) {

    var keywords: String? = null
    private var list: ArrayList<PlayListSearchBean.ResultBean.PlaylistsBean>? = null
    var listener: OnPlayListClickListener? = null
    private lateinit var binding: ItemPlaylistFragmentBinding

    override fun notifyDataSetChanged(dataList: ArrayList<PlayListSearchBean.ResultBean.PlaylistsBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemPlaylistFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(mContext, list!![position], keywords!!)
            holder.setListener(listener, position)
        }
    }

    override fun getItemCount() = list?.size ?: 0

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun setBean(context: Context, bean: PlayListSearchBean.ResultBean.PlaylistsBean, keywords: String) {
            Glide.with(context)
                    .load(bean.coverImgUrl)
                    .into(binding.ivCover)

            if (bean.name.contains(keywords)) {
                val start = bean.name.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.name
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvPlaylistName.text = style.toString()
            } else {
                binding.tvPlaylistName.text = bean.name
            }

            var playCount = bean.playCount
            val count = if (playCount >= 10000) {
                playCount /= 10000
                "${playCount}万次"
            } else {
                "${playCount}次"
            }
            binding.tvPlaylistInfo.text = "${bean.trackCount}首, by ${bean.creator!!.nickname}, 播放${count}"
        }

        fun setListener(listener: OnPlayListClickListener?, position: Int) {
            binding.llItem.setOnClickListener {
                listener?.onPlayListClick(position)
            }
        }
    }

    interface OnPlayListClickListener {
        fun onPlayListClick(position: Int)
    }
}