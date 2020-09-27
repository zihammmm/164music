package com.zihany.cloudmusic.search.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemSingerSearchBinding
import com.zihany.cloudmusic.search.bean.SingerSearchBean

class SingerSearchAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, SingerSearchBean.ResultBean.ArtistsBean>(mContext) {

    var keywords: String? = null
    private var list: ArrayList<SingerSearchBean.ResultBean.ArtistsBean>? = null
    var listener: OnSingerClickListener? = null
    private lateinit var binding: ItemSingerSearchBinding

    override fun notifyDataSetChanged(dataList: ArrayList<SingerSearchBean.ResultBean.ArtistsBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSingerSearchBinding.inflate(inflater, parent, false)
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

        fun setBean(context: Context, bean: SingerSearchBean.ResultBean.ArtistsBean, keywords: String) {
            Glide.with(context)
                    .load(bean.picUrl)
                    .into(binding.ivSingeravatar)
            val name =
                    if (!TextUtils.isEmpty(bean.trans)) {
                        "${bean.name}(${bean.trans})"
                    } else {
                        bean.name
                    }
            if (name.contains(keywords)) {
                val start = name.indexOf(keywords)
                val end = start + keywords.length
                val style = SpannableStringBuilder(name)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvSingername.text = style.toString()
            } else {
                binding.tvSingername.text = name
            }
        }

        fun setListener(listener: OnSingerClickListener?, position: Int) {
            binding.rlSinger.setOnClickListener {
                listener?.onSingerClick(position)
            }
        }

    }

    interface OnSingerClickListener {
        fun onSingerClick(position: Int)
    }
}