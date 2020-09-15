package com.zihany.cloudmusic.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemPlaylistBinding
import com.zihany.cloudmusic.main.bean.PlaylistBean

class PlayListAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, PlaylistBean>(mContext) {
    companion object {
        const val TAG = "PlayListAdapter"
    }

    private var list: MutableList<PlaylistBean>? = null
    var listener: OnPlayListClickListener? = null
    var type = 0
    private lateinit var binding: ItemPlaylistBinding

    interface OnPlayListClickListener {
        fun onClickListener(position: Int)
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun setPlayListInfo(context: Context, bean: PlaylistBean) {
            Glide.with(context)
                    .load(bean.playlistCoverUrl)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(binding.ivPlaylist)
            binding.tvPlaylistName.text = bean.playlistName
        }

        fun onSetListClickListener(listener: OnPlayListClickListener?, position: Int) {
            binding.rlPlaylist.setOnClickListener {
                listener?.onClickListener(position)
            }
        }
    }

    override fun notifyDataSetChanged(dataList: MutableList<PlaylistBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemPlaylistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val bean = list!![position]
            holder.setPlayListInfo(mContext, bean)
            holder.onSetListClickListener(listener, position)
        }
    }

    override fun getItemCount(): Int {
        return when {
            list == null -> {
                0
            }
            type == 1 -> {
                6
            }
            else -> {
                list!!.size
            }
        }
    }

}