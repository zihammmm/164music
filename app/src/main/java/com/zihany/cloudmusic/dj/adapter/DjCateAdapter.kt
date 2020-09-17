package com.zihany.cloudmusic.dj.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemDjCateBinding
import com.zihany.cloudmusic.dj.bean.DjCateListBean
import com.zihany.cloudmusic.dj.mvvm.view.RadioListActivity

class DjCateAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, DjCateListBean.CategoriesBean>(mContext) {
    private var list = ArrayList<DjCateListBean.CategoriesBean>()
    private lateinit var binding: ItemDjCateBinding

    override fun notifyDataSetChanged(dataList: ArrayList<DjCateListBean.CategoriesBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemDjCateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(position)
            holder.setListener(position)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view) {

        fun setBean(i: Int) {
            val bean = list[i]
            Glide.with(mContext)
                    .load(bean.pic84x84IdUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivIcon)
            binding.tvCateName.text = bean.name
            if (i >= 2) {
                binding.tvTop.visibility = View.GONE
            }
        }

        fun setListener(i: Int) {
            binding.rlItem.setOnClickListener {
                val intent = Intent(mContext, RadioListActivity::class.java)
                intent.putExtra(RadioListActivity.TYPE, list[i].id)
                intent.putExtra(RadioListActivity.TITLE_NAME, list[i].name)
                mContext.startActivity(intent)
            }
        }

    }
}