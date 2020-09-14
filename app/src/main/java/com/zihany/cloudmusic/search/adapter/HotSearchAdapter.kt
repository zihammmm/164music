package com.zihany.cloudmusic.search.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemHotSearchBinding
import com.zihany.cloudmusic.search.bean.HotSearchDetailBean

class HotSearchAdapter constructor(context: Context)
    : BaseAdapter<RecyclerView.ViewHolder, HotSearchDetailBean>(context) {
    private lateinit var list: HotSearchDetailBean
    lateinit var listener: OnHotSearchAdapterClickListen
    private lateinit var binding: ItemHotSearchBinding

    inner class ViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun setBean(list: HotSearchDetailBean, position: Int) {
            binding.tvName.text = list.data!![position].searchWord
            binding.tvNumber.text = (position+1).toString()
            binding.tvCount.text = list.data!![position].score.toString()
            binding.tvDescription.text = list.data!![position].content
        }

        fun setListener(listener: OnHotSearchAdapterClickListen, i: Int) {
            binding.rlHotSearch.setOnClickListener {
                listener.onHotSearchClick(i)
            }
        }
    }

    interface OnHotSearchAdapterClickListen {
        fun onHotSearchClick(position: Int)
    }

    override fun notifyDataSetChanged(dataList: MutableList<HotSearchDetailBean>) {
        list = dataList[0]
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemHotSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list.data?.size?:0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(list, position)
            holder.setListener(listener, position)
        }
    }
}