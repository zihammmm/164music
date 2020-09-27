package com.zihany.cloudmusic.search.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemPlaylistFragmentBinding
import com.zihany.cloudmusic.search.bean.RadioSearchBean

class RadioSearchAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, RadioSearchBean.ResultBean.DjRadiosBean>(mContext) {

    var keywords: String? = null
    private var list: ArrayList<RadioSearchBean.ResultBean.DjRadiosBean>? = null
    var listener: OnRadioClickListener? = null

    private lateinit var binding: ItemPlaylistFragmentBinding

    override fun notifyDataSetChanged(dataList: ArrayList<RadioSearchBean.ResultBean.DjRadiosBean>) {
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
        }
    }

    override fun getItemCount() = list?.size ?: 0

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        fun setBean(context: Context, bean: RadioSearchBean.ResultBean.DjRadiosBean, keywords: String) {
            Glide.with(context)
                    .load(bean.picUrl)
                    .into(binding.ivCover)

            if (bean.name.contains(keywords)) {
                val start = bean.name.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.name
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvPlaylistName.text = style.toString()
            } else {
                binding.tvPlaylistName.text = bean.name
            }

            if (bean.dj?.nickname!!.contains(keywords)) {
                val start = bean.dj?.nickname!!.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.name
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvPlaylistName.text = style.toString()
            } else {
                binding.tvPlaylistName.text = bean.name
            }

            if (bean.dj?.nickname!!.contains(keywords)) {
                val start = bean.dj!!.nickname.indexOf(keywords)
                val end = start + keywords.length
                val resString = bean.dj!!.nickname
                val style = SpannableStringBuilder(resString)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvPlaylistName.text = style.toString()
            } else {
                binding.tvPlaylistName.text = bean.dj!!.nickname
            }

        }

    }

    interface OnRadioClickListener {
        fun onRadioClick(position: Int)
    }
}