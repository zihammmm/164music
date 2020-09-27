package com.zihany.cloudmusic.search.adapter

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
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemFeedSearchBinding
import com.zihany.cloudmusic.search.bean.MvBean
import com.zihany.cloudmusic.util.TimeUtil
import com.zihany.cloudmusic.video.mvvm.view.VideoActivity
import java.util.regex.Pattern

class FeedAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, MvBean>(mContext) {

    private var list: ArrayList<MvBean>? = null
    var type = 0
    var keywords = ""
    private lateinit var binding: ItemFeedSearchBinding

    override fun notifyDataSetChanged(dataList: ArrayList<MvBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemFeedSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(mContext, list!![position], keywords, type)
            holder.setListener(mContext, position)
        }
    }

    override fun getItemCount() = list?.size ?: 0

    inner class ViewHolder constructor(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        fun setBean(context: Context, videoBean: MvBean, keywords: String, type: Int) {
            if (!judgeContainsStr(videoBean.vid)) {
                binding.tvMv.visibility = View.VISIBLE
            } else {
                binding.tvMv.visibility = View.GONE
            }

            if (type == 1) {
                if (videoBean.title.contains(keywords)) {
                    val start = videoBean.title.indexOf(keywords)
                    val end = start + keywords.length
                    val resString = videoBean.title
                    val style = SpannableStringBuilder(resString)
                            .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvName.text = style.toString()
                } else {
                    binding.tvName.text = videoBean.title
                }
                if (videoBean.creator!![0].userName.contains(keywords)) {
                    val start = videoBean.creator!![0].userName.indexOf(keywords)
                    val end = start + keywords.length
                    val resString = videoBean.creator!![0].userName
                    val style = SpannableStringBuilder(resString)
                            .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvName.text = style.toString()
                } else {
                    binding.tvName.text = videoBean.title
                }
            } else if (type == 2) {
                binding.tvName.text = videoBean.title
                binding.tvCreator.text = TimeUtil.getTimeNoYMDH(videoBean.duration)
            }
            Glide.with(context)
                    .load(videoBean.coverUrl)
                    .into(binding.ivCover)
        }

        fun setListener(context: Context, position: Int) {
            binding.rlFeed.setOnClickListener {
                val intent = Intent(context, VideoActivity::class.java)
                context.startActivity(intent)
            }
        }

        fun judgeContainsStr(cardNum: String): Boolean {
            val regex = ".*[a-zA-Z]+.*"
            val m = Pattern.compile(regex).matcher(cardNum)
            return m.matches()
        }
    }
}