package com.zihany.cloudmusic.dj.mvvm.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityRadioListBinding
import com.zihany.cloudmusic.dj.adapter.RadioListAdapter
import com.zihany.cloudmusic.dj.bean.DjBean
import com.zihany.cloudmusic.dj.bean.DjPayGiftBean
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.util.LogUtil

class RadioListActivity: BaseActivity() {
    companion object {
        const val TAG = "RadioListActivity"
        const val TITLE_NAME = "titleName"
        const val TYPE = "type"
    }

    private lateinit var adapter: RadioListAdapter
    private lateinit var binding: ActivityRadioListBinding

    override fun initData() {
        val intent = intent
        intent?.apply {
            val titleName = this.getStringExtra(TITLE_NAME)
            setLeftTitleText(titleName, "#ffffff")
            setBackBtn("#ffffff")

            binding.rvDjList.layoutManager = LinearLayoutManager(this@RadioListActivity)
            adapter = RadioListAdapter(this@RadioListActivity)
            binding.rvDjList.adapter = adapter

            val type = intent.getIntExtra(TYPE, 0)
            showDialog()
//            if (type != 0) {
//                viewModel.getDjRecommendType(type)
//            }else {
//                viewModel.getDjPayGift(30, 1)
//            }
        }
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }

    fun onGetDjPayGiftSuccess(bean: DjPayGiftBean) {
        hideDialog()
        LogUtil.d(TAG, "onGetDjPayGiftSuccess: $bean")
        val djList = ArrayList<DjBean>()
        val djBeans = bean.data?.list!!
        for (d: DjPayGiftBean.DataBean.ListBean in djBeans) {
            val djBean = DjBean()
            djBean.djName = d.name
            djBean.rid = d.id
            djBean.rcmdName = d.rcmdText
            djBean.coverUrl = d.picUrl
            djBean.artistName = ""
            djBean.price = d.originalPrice / 100
            djBean.programCount = d.programCount
            djBean.registerCount = -1
            djBean.subed = false
            djList.add(djBean)
        }
        adapter.notifyDataSetChanged(djList)
    }

    fun onGetDjPayGiftFail(msg: String) {

    }
}