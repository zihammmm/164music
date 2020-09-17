package com.zihany.cloudmusic.dj.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemDjListBinding
import com.zihany.cloudmusic.dj.bean.DjBean
import com.zihany.cloudmusic.dj.mvvm.view.RadioActivity
import com.zihany.cloudmusic.util.LogUtil

class RadioListAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, DjBean>(mContext) {
    companion object {
        const val TAG = "RadioListAdapter"
    }
    private var list = ArrayList<DjBean>()
    private lateinit var binding: ItemDjListBinding

    override fun notifyDataSetChanged(dataList: ArrayList<DjBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemDjListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(list[position])
            holder.setListener(position)
        }
    }

    inner class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun setBean(djBean: DjBean) {
            Glide.with(mContext)
                    .load(djBean.coverUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.djCover)

            binding.tvName.text = djBean.djName
            if (djBean.price != 0) {
                binding.tvPrice.visibility = View.VISIBLE
                binding.tvPrice.text = "￥${djBean.price}"
            }
            binding.tvRecm.text = djBean.rcmdName
            if (djBean.registerCount == -1) {
                binding.tvDjInfo.text = "最新上架"
            }else {
                binding.tvDjInfo.text = "节目:${djBean.programCount}  订阅:${djBean.registerCount}"
            }
        }

        fun setListener(position: Int) {
            if (list[position].price != 0) {
                LogUtil.d(TAG, "空")
            }else {
                binding.rlItem.setOnClickListener {
                    val intent = Intent(mContext, RadioActivity::class.java)
                    intent.putExtra(RadioActivity.IS_SUB, list[position].subed)
                    intent.putExtra(RadioActivity.SUB_COUNT, list[position].registerCount)
                    intent.putExtra(RadioActivity.RID, list[position].rid)
                    intent.putExtra(RadioActivity.COVER_URL, list[position].coverUrl)
                    intent.putExtra(RadioActivity.RADIO_NAME, list[position].djName)
                    mContext.startActivity(intent)
                }
            }
        }
    }

}