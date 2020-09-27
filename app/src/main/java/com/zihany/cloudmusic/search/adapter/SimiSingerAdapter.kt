package com.zihany.cloudmusic.search.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemSimiSingerBinding
import com.zihany.cloudmusic.databinding.ItemSingerSearchBinding
import com.zihany.cloudmusic.search.bean.SimiSingerBean

class SimiSingerAdapter constructor(
        private val context: Context
) : BaseAdapter<RecyclerView.ViewHolder, SimiSingerBean.ArtistsBean>(context) {

    companion object {
        const val TAG = "SimiSingerAdapter"
    }

    private var list = ArrayList<SimiSingerBean.ArtistsBean>()
    var listener: OnSimiSingerClickListener? = null

    private lateinit var binding: ItemSimiSingerBinding

    interface OnSimiSingerClickListener {
        fun onSimiClick(position: Int)
    }

    override fun notifyDataSetChanged(dataList: ArrayList<SimiSingerBean.ArtistsBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSimiSingerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(context, list[position])
            holder.setListener(listener, position)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun setBean(context: Context, bean: SimiSingerBean.ArtistsBean) {
            Glide.with(context)
                    .load(bean.picUrl)
                    .into(binding.ivCover)
            binding.tvName.text = bean.name
        }

        fun setListener(listener: OnSimiSingerClickListener?, position: Int) {
            binding.rlSimi.setOnClickListener {
                listener?.onSimiClick(position)
            }
        }
    }
}