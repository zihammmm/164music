package com.zihany.cloudmusic.base

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH: RecyclerView.ViewHolder, T> constructor(context: Context): RecyclerView.Adapter<VH>() {
    companion object {
        val VIEW_TYPE_EMPTY = 0
        val VIEW_TYPE_ITEM = 1
    }

    var inflater: LayoutInflater = LayoutInflater.from(context)

    abstract fun notifyDataSetChanged(dataList: MutableList<T>)
}