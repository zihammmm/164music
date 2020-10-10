package com.zihany.cloudmusic.search.mvvm.view

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.database.SearchHistoryDaoOp
import com.zihany.cloudmusic.databinding.ActivitySearchBinding
import com.zihany.cloudmusic.search.adapter.HotSearchAdapter
import com.zihany.cloudmusic.search.bean.HotSearchDetailBean
import com.zihany.cloudmusic.search.bean.SearchHistoryBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel
import com.zihany.cloudmusic.widget.MusicDialog
import com.zihany.cloudmusic.widget.SearchHistoryTagLayout

class SearchActivity : BaseActivity() {
    companion object {
        const val TAG = "SearchActivity"
        const val KEYWORDS = "keywords"
    }

    private lateinit var adapter: HotSearchAdapter
    private var searchDetailBean: HotSearchDetailBean? = null
    private var stringList = ArrayList<SearchHistoryBean>()
    private lateinit var binding: ActivitySearchBinding
    private val searchListener = object : HotSearchAdapter.OnHotSearchAdapterClickListen {
        override fun onHotSearchClick(position: Int) {
            searchDetailBean?.let {
                val keywords = it.data!![position].searchWord
                searchSong(keywords)
            }
        }
    }
    private val tagListener = object : SearchHistoryTagLayout.OnHistoryTagClickListener {
        override fun onItemClick(position: Int) {
            val keywords = stringList[position].keyowrds
            searchSong(keywords)
        }
    }

    private var isDeleteDialog: MusicDialog? = null

    override fun initData() {
        setBackBtn("#ffffff")
        setEditText("#ccffffff")
        setRightSearchButton()

        adapter = HotSearchAdapter(this)
        binding.rvHotsearch.layoutManager = LinearLayoutManager(this)
        binding.rvHotsearch.adapter = adapter
        adapter.listener = searchListener
    }

    override fun initView() {

    }

    override fun startObserve() {

    }

    override fun onClick(view: View) {

    }

    override fun onResume() {
        super.onResume()

        if (SearchHistoryDaoOp.queryAll() != null) {
            stringList = SearchHistoryDaoOp.queryAll() as ArrayList<SearchHistoryBean>
        }
        binding.tlSearchhistory.addHistoryText(stringList, tagListener)
    }

    private fun showIsDeleteAllDialog() {
        if (isDeleteDialog == null) {
            isDeleteDialog = MusicDialog.Builder(this)
                    .setMsg(R.string.should_delete_all_search_history)
                    .setNegativeText(R.string.dialog_cancel)
                    .setPositiveText(R.string.dialog_search_history_clear)
                    .setNegativeClickListener(DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    }
                    ).setPositiveClickListener(DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                        SearchHistoryDaoOp.deleteAllData()

                    })
                    .create()
        }
        if (!isDeleteDialog!!.isShowing) {
            isDeleteDialog!!.show()
        }
    }

    private fun searchSong(keywords: String) {
        stringList.add(SearchHistoryBean(keywords))
        if (stringList.size > 10) {
            stringList.removeAt(0)
        }

        for (i: Int in 0 until stringList.size) {
            if (stringList[i].keyowrds == keywords) {
                stringList.removeAt(i)
                break
            }
        }
        SearchHistoryDaoOp.saveData(stringList)

        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra(KEYWORDS, keywords)
        startActivity(intent)
    }
}