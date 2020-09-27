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
import com.zihany.cloudmusic.databinding.ItemUserSearchBinding
import com.zihany.cloudmusic.search.bean.UserSearchBean

class UserSearchAdapter constructor(
        private val mContext: Context
) : BaseAdapter<RecyclerView.ViewHolder, UserSearchBean.ResultBean.UserprofilesBean>(mContext) {

    var keywords: String? = null
    private var list: ArrayList<UserSearchBean.ResultBean.UserprofilesBean>? = null
    var listener: OnUserClickListener? = null

    private lateinit var binding: ItemUserSearchBinding

    interface OnUserClickListener {
        companion object {
            const val USER_CHECK = 1
            const val USER_FOLLOW = 2
        }
        fun onUserClick(position: Int, type: Int)
    }

    override fun notifyDataSetChanged(dataList: ArrayList<UserSearchBean.ResultBean.UserprofilesBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemUserSearchBinding.inflate(inflater, parent, false)
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
    ): RecyclerView.ViewHolder(view) {
        fun setBean(context: Context, bean: UserSearchBean.ResultBean.UserprofilesBean, keywords: String) {
            Glide.with(context)
                    .load(bean.avatarUrl)
                    .into(binding.ivAvatar)

            if (!bean.followed) {
                binding.tvFollow.visibility = View.VISIBLE
                binding.tvAlreadyFollow.visibility = View.GONE
            } else {
                binding.tvFollow.visibility = View.GONE
                binding.tvAlreadyFollow.visibility = View.VISIBLE
            }

            if (bean.gender == 0) {
                binding.ivGender.setBackgroundResource(R.drawable.shape_female)
            } else if (bean.gender == 1) {
                binding.ivGender.setBackgroundResource(R.drawable.shape_male)
            }
            binding.ivGender.visibility = View.VISIBLE

            if (bean.nickname.contains(keywords)) {
                val start = bean.nickname.indexOf(keywords)
                val end = start + keywords.length
                val style = SpannableStringBuilder(bean.nickname)
                        .setSpan(ForegroundColorSpan(Color.parseColor(context.getString(R.string.colorBlue))),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvName.text = style.toString()
            } else {
                binding.tvName.text = bean.nickname
            }
        }

        fun setListener(listener: OnUserClickListener?, position: Int) {
            binding.rlUser.setOnClickListener {
                listener?.onUserClick(position, OnUserClickListener.USER_CHECK)
            }

            binding.tvFollow.setOnClickListener {
                listener?.onUserClick(position, OnUserClickListener.USER_FOLLOW)
            }
        }
    }
}