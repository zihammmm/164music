package com.zihany.cloudmusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import android.widget.TextView
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.databinding.LayoutSearchhistoryTagBinding
import com.zihany.cloudmusic.search.bean.SearchHistoryBean

class SearchHistoryTagLayout constructor(private val mContext: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : HorizontalScrollView(mContext, attrs, defStyleAttr) {

    private val stringList: MutableList<SearchHistoryBean> = ArrayList()
    private val textViewList: MutableList<TextView> = ArrayList()
    private lateinit var listener: OnHistoryTagClickListener
    private val rlHsv: RelativeLayout = LayoutSearchhistoryTagBinding.inflate(LayoutInflater.from(mContext), this, false).rlHsv

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    private fun initListener() {
        val size = textViewList.size
        for (i: Int in textViewList.indices) {
            textViewList[i].setOnClickListener {
                listener.onItemClick(size - i - 1)
            }
        }
    }

    interface OnHistoryTagClickListener {
        fun onItemClick(position: Int)
    }

    fun addHistoryText(list: MutableList<SearchHistoryBean>, listener: OnHistoryTagClickListener) {
        this.listener = listener
        stringList.clear()
        rlHsv.removeAllViews()
        stringList.add(SearchHistoryBean(""))
        textViewList.clear()
        stringList.addAll(list)

        for (i: Int in stringList.indices) {
            val lp = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            val tvHistory = TextView(mContext)
            tvHistory.id = i
            if (i == 0) {
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                tvHistory.visibility = GONE
                tvHistory.text = stringList[0].keyowrds
            }else {
                lp.addRule(RelativeLayout.RIGHT_OF, rlHsv.getChildAt(i-1).id)
                tvHistory.text = stringList[stringList.size - 1].keyowrds
            }
            tvHistory.setTextAppearance(R.style.SearchHistoryTextStyle)
            tvHistory.setBackgroundResource(R.drawable.shape_tag)
            tvHistory.setPadding(40, 10, 40, 10)
            if (i != 1) {
                lp.leftMargin = 25
            }
            tvHistory.layoutParams = lp
            rlHsv.addView(tvHistory)
            if (i != 0) {
                textViewList.add(tvHistory)
            }
        }
        initListener()
    }
}