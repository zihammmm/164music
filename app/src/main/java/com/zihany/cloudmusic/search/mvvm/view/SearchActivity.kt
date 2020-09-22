package com.zihany.cloudmusic.search.mvvm.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.database.SearchHistoryDaoOp
import com.zihany.cloudmusic.databinding.ActivitySearchBinding
import com.zihany.cloudmusic.search.adapter.HotSearchAdapter
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel
import com.zihany.cloudmusic.widget.MusicDialog

class SearchActivity : BaseActivity<SearchViewModel>() {
    companion object {
        const val TAG = "SearchActivity"
        const val KEYWORDS = "keywords"
    }

    private lateinit var adapter: HotSearchAdapter
    private val stringList: MutableList<String> = ArrayList()
    private lateinit var binding: ActivitySearchBinding
    private val searchListener: HotSearchAdapter.OnHotSearchAdapterClickListen
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

    override fun onResume() {
        super.onResume()

        if (SearchHistoryDaoOp.queryAll() != null) {

        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
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
                    ).setPositiveClickListener(DialogInterface.OnClickListener {
                        dialog, _ ->
                        dialog.dismiss()
                        SearchHistoryDaoOp.deleteAllData()

                    })
                    .create()
        }
        if (!isDeleteDialog!!.isShowing) {
            isDeleteDialog!!.show()
        }
    }
}