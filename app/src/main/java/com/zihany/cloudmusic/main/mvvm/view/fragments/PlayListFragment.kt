package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.main.adapter.EndlessRecyclerOnScrollListener
import com.zihany.cloudmusic.main.adapter.PlayListAdapter
import com.zihany.cloudmusic.main.bean.PlaylistBean
import com.zihany.cloudmusic.main.bean.RecommendPlayListBean
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel

class PlayListFragment constructor(private val type: String) : BaseFragment<WowViewModel>() {
    companion object {
        const val TAG = "PlayListFragment"
        private val INIT_LOAD_LINE = 3
        private val TOTAL_LOAD_LINE = 30
    }

    private lateinit var adapter: PlayListAdapter
    private var playList = ArrayList<RecommendPlayListBean.PlaylistsBean>()
    private var list = ArrayList<PlaylistBean>()
    private lateinit var manager: GridLayoutManager
    private lateinit var binding: FragmentRecyclerviewBinding
    private var totalPage = 0

    private val listener = object : PlayListAdapter.OnPlayListClickListener {
        override fun onClickListener(position: Int) {
            if (playList.isNotEmpty()) {
                val intent = Intent(activity, PlayListActivity::class.java)
                val bean = playList[position]
                val playlistName = bean.name
                intent.putExtra(WowFragment.PLAYLIST_NAME, playlistName)
                val playlistPicUrl = bean.coverImgUrl
                intent.putExtra(WowFragment.PLAYLIST_PICURL, playlistPicUrl)
                val playlistCreatorNickName = bean.creator?.nickname
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playlistCreatorNickName)
                val playlistCreatorAvatarUrl = bean.creator?.avatarUrl
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, playlistCreatorAvatarUrl)
                val playlistId = bean.id
                intent.putExtra(WowFragment.PLAYLIST_ID, playlistId)
                startActivity(intent)
            }
        }

    }

    init {
        fragmentTitle = type
    }

    override fun initData() {
        list.clear()
        adapter = PlayListAdapter(context!!)
        adapter.type = 2
        adapter.listener = listener
        manager = GridLayoutManager(context!!, 3)
        binding.rv.layoutManager = manager
        binding.rv.addOnScrollListener(object : EndlessRecyclerOnScrollListener(manager) {
            override fun onLoadMore(currentPage: Int) {
                lazyLoadPlayList(3)
            }

        })
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter
        playList.clear()
        showDialog()
    }

    private fun lazyLoadPlayList(page: Int) {
        if (playList.isEmpty() || totalPage >= 90) {
            return
        }
        for (i: Int in totalPage until (totalPage + page * INIT_LOAD_LINE)) {
            val beanInfo = PlaylistBean()
            beanInfo.playlistName = playList[i].name.toString()
            beanInfo.playlistCoverUrl = playList[i].coverImgUrl.toString()
            list.add(beanInfo)
        }
        totalPage += page * INIT_LOAD_LINE
        adapter.notifyDataSetChanged(list)
    }
}