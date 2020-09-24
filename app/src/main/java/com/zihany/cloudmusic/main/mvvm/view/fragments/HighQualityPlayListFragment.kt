package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentHighQualityBinding
import com.zihany.cloudmusic.main.adapter.EndlessRecyclerOnScrollListener
import com.zihany.cloudmusic.main.adapter.PlayListAdapter
import com.zihany.cloudmusic.main.bean.HighQualityPlayListBean
import com.zihany.cloudmusic.main.bean.PlaylistBean
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.util.LogUtil

class HighQualityPlayListFragment constructor(private var type: String): BaseFragment<WowViewModel>() {
    companion object {
        const val TAG = "HighQualityPlayListFrag"
        const val INIT_LOAD_LIMIT = 21
    }
    private lateinit var binding: FragmentHighQualityBinding
    private var playList = ArrayList<HighQualityPlayListBean.PlaylistsBean>()
    private var list = ArrayList<PlaylistBean>()
    private var adapter: PlayListAdapter? = null
    private var manager: GridLayoutManager? = null

    private val listener = object : PlayListAdapter.OnPlayListClickListener {
        override fun onClickListener(position: Int) {
            if (playList.isNotEmpty()) {
                val intent = Intent(activity, PlayListActivity::class.java)
                val bean = playList[position + 3]
                val playListName = bean.name
                intent.putExtra(WowFragment.PLAYLIST_NAME, playListName)
                val playListPicUrl = bean.coverImgUrl
                intent.putExtra(WowFragment.PLAYLIST_PICURL, playListPicUrl)
                val playListCreatorNickName = bean.creator?.nickname
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playListCreatorNickName)
                val playListCreatorAvatarUrl = bean.creator?.avatarUrl
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, playListCreatorAvatarUrl)
                val playListId = bean.id
                intent.putExtra(WowFragment.PLAYLIST_ID, playListId)
                startActivity(intent)
            }
        }

    }

    init {
        fragmentTitle = type
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHighQualityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {
    }

    override fun initData() {
        list.clear()
        playList.clear()

        adapter = PlayListAdapter(context!!)
        adapter?.type = 2
        adapter?.listener = listener
        manager = GridLayoutManager(context!!, 3)
        binding.rv.layoutManager = manager
        binding.rv.addOnScrollListener(object : EndlessRecyclerOnScrollListener(manager!!) {
            override fun onLoadMore(currentPage: Int) {
                LogUtil.d(TAG, "onLoadMore")
            }
        })
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter

        showDialog()

    }
}