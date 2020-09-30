package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.content.Intent
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.search.adapter.SingerSearchAdapter
import com.zihany.cloudmusic.search.bean.SingerSearchBean
import com.zihany.cloudmusic.search.mvvm.view.SingerActivity
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class SingerSearchFragment constructor(
        private val type: String?
) : BaseFragment<SearchViewModel>() {

    constructor() : this(null)

    private lateinit var binding: FragmentRecyclerviewBinding
    private lateinit var adapter: SingerSearchAdapter
    private val searchType = 100
    private var needRefresh = false
    private var list = ArrayList<SingerSearchBean.ResultBean.ArtistsBean>()
    private var keywords: String? = null

    private val listener = object : SingerSearchAdapter.OnSingerClickListener {
        override fun onSingerClick(position: Int) {
            val intent = Intent(activity, SingerActivity::class.java)
            intent.putExtra(SingerActivity.SINGER_ID, list[position].id)
            intent.putExtra(SingerActivity.SINGER_PICURL, list[position].picUrl)
            val name = if (!TextUtils.isEmpty(list[position].trans)) {
                "${list[position].name}(${list[position].trans})"
            } else {
                list[position].name
            }
            intent.putExtra(SingerActivity.SINGER_NAME, name)
            startActivity(intent)
        }
    }

    init {
        fragmentTitle = type
    }

    override fun initData() {
        adapter = SingerSearchAdapter(context!!)
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter
        adapter.listener = listener
    }

    override fun onVisible() {
        super.onVisible()
        if (needRefresh) {
            needRefresh = false
            showDialog()
        }
    }
}