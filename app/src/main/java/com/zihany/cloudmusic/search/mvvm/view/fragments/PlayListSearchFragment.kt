package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.search.adapter.PlayListSearchAdapter
import com.zihany.cloudmusic.search.bean.PlayListSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel

class PlayListSearchFragment constructor(
        private val type: String?
) : BaseFragment<SearchViewModel>() {

    companion object {
        const val TAG = "PlayListSearchFragment"
    }

    constructor(): this(null)

    private lateinit var binding: FragmentRecyclerviewBinding
    private val searchType = 1000
    private var keywords: String? = null
    private var needRefresh = false
    private var playlistList = ArrayList<PlayListSearchBean.ResultBean.PlaylistsBean>()
    private lateinit var adapter: PlayListSearchAdapter
    private val listener = object : PlayListSearchAdapter.OnPlayListClickListener {
        override fun onPlayListClick(position: Int) {
            val intent = Intent(activity, PlayListActivity::class.java)
            intent.putExtra(WowFragment.PLAYLIST_NAME, playlistList[position].name)
            intent.putExtra(WowFragment.PLAYLIST_PICURL, playlistList[position].coverImgUrl)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playlistList[position].creator?.nickname)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, "")
            intent.putExtra(WowFragment.PLAYLIST_ID, playlistList[position].id)
            startActivity(intent)
        }
    }

    init {
        fragmentTitle = type
    }

    override fun initData() {
        adapter = PlayListSearchAdapter(context!!)
        adapter.keywords = keywords
        adapter.listener = listener
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context)

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
}