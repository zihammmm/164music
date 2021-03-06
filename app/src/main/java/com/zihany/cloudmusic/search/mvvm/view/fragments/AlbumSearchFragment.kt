package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.search.adapter.AlbumAdapter
import com.zihany.cloudmusic.search.bean.AlbumSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class AlbumSearchFragment constructor(
        private val type: String?
) : BaseFragment<FragmentRecyclerviewBinding>(R.layout.fragment_recyclerview) {

    constructor() : this(null)

    private val searchType = 10
    private lateinit var adapter: AlbumAdapter
    private var keywords: String? = null
    private var albumList = ArrayList<AlbumSearchBean.ResultBean.AlbumsBean>()
    private var adapterList = ArrayList<AlbumAdapter>()
    private var needRefresh = false

    private val listener = object : AlbumAdapter.OnAlbumClickListener {
        override fun onAlbumClick(position: Int) {
            val intent = Intent(context, PlayListActivity::class.java)
            intent.putExtra(WowFragment.PLAYLIST_PICURL, albumList[position].blurPicUrl)
            intent.putExtra(WowFragment.PLAYLIST_NAME, albumList[position].name)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, "")
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, "")
            intent.putExtra(WowFragment.PLAYLIST_ID, albumList[position].id)
            startActivity(intent)
        }

    }

    init {
        fragmentTitle = type
    }

    override fun initData() {
        adapter = AlbumAdapter(context!!)
        adapter.keywords = keywords
        adapter.listener = listener
        adapter.type = 1
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        keywords?.let {
            showDialog()
        }
    }

    override fun onVisible() {
        super.onVisible()
        if (needRefresh) {
            needRefresh = false
            showDialog()
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
}