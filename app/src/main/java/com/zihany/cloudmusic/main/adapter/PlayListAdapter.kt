package com.zihany.cloudmusic.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.main.bean.PlaylistBean

class PlayListAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, PlaylistBean>(mContext) {
    companion object {
        const val TAG = "PlayListAdapter"
    }
    private lateinit var list: MutableList<PlaylistBean>
    private lateinit var listener: OnPlayListClickListener
    private var type = 0

    interface OnPlayListClickListener {

    }

    inner class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view) {
        fun setPlayListInfo(context: Context, bean: PlaylistBean) {

        }

        fun onSetListClickListener(listener: OnPlayListClickListener, position: Int) {

        }
    }

    override fun notifyDataSetChanged(dataList: MutableList<PlaylistBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}