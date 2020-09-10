package com.zihany.Cloudmusic.personal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zihany.Cloudmusic.R
import com.zihany.Cloudmusic.base.BaseAdapter
import com.zihany.Cloudmusic.databinding.ItemPlaylistFragmentBinding
import com.zihany.Cloudmusic.personal.bean.PlayListItemBean

class UserPlaylistAdapter constructor(private val context: Context)
    : BaseAdapter<RecyclerView.ViewHolder, PlayListItemBean>(context) {

    private var list: MutableList<PlayListItemBean>? = null
    var listener: OnPlayListItemClickListener? = null
    var nickName: String? = null
    var isShowSmartPlay = false
    private var binding: ItemPlaylistFragmentBinding = ItemPlaylistFragmentBinding()

    override fun notifyDataSetChanged(dataList: MutableList<PlayListItemBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserPlaylistAdapter.ViewHolder) {
            val bean = list!![position]
            holder.setPlayListItemInfo(context, bean, position)
            holder.onSetListClickListener(listener!!, position)
        }
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setPlayListItemInfo(context: Context, bean: PlayListItemBean, position: Int) {
            Glide.with(context)
                    .load(bean.coverUrl)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(binding.ivCover)
            binding.tvPlaylistName.text = bean.playListName
            var playCount = bean.playCount
            val count: String?
            if (playCount >= 10000) {
                playCount /= 10000
                count = "${playCount}万次"
            }else {
                count = "${playCount}次"
            }
            if (nickName == bean.playListCreator) {
                binding.tvPlaylistInfo.text = "${bean.songNumber}首,播放${count}"
            }else {
                binding.tvPlaylistInfo.text = "${bean.songNumber}首,by ${bean.playListCreator},播放${count}"
            }
            if (isShowSmartPlay && position == 0) {
                binding.ivSmartPlay.visibility = View.VISIBLE
            }else {
                binding.ivSmartPlay.visibility = View.GONE
            }
        }

        fun onSetListClickListener(listener: OnPlayListItemClickListener?, i: Int) {
            binding.ivSmartPlay.setOnClickListener {
                listener?.onSmartPlayClick(adapterPosition)
             }

            binding.llItem.setOnClickListener {
                listener?.onPlayListItemClick(i)
            }
        }
    }

    interface OnPlayListItemClickListener {
        fun onPlayListItemClick(position: Int)
        fun onSmartPlayClick(position: Int)
    }
}