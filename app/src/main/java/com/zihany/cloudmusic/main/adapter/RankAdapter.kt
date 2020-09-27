package com.zihany.cloudmusic.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemToplistBinding
import com.zihany.cloudmusic.main.bean.TopListBean

class RankAdapter constructor(
        private val context: Context
) : BaseAdapter<RecyclerView.ViewHolder, TopListBean.ListBean>(context) {

    companion object {
        const val TAG = "RankAdapter"
    }
    private var list = ArrayList<TopListBean.ListBean>()
    var listener: OnTopListClickListener? = null

    private lateinit var binding: ItemToplistBinding

    override fun notifyDataSetChanged(dataList: ArrayList<TopListBean.ListBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemToplistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(list[position])
            holder.setOnClickListener(listener, position)
        }
    }

    override fun getItemCount() = list.size

    interface OnTopListClickListener {
        fun onClickTopList(position: Int)
    }

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun setBean(bean: TopListBean.ListBean) {
            Glide.with(context)
                    .load(bean.coverImgUrl)
                    .into(binding.ivToplist)
            binding.tvToplistName.text = bean.name
        }

        fun setOnClickListener(listener: OnTopListClickListener?, position: Int) {
            binding.rlToplist.setOnClickListener {
                listener?.onClickTopList(position)
            }
        }
    }


}