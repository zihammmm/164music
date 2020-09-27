package com.zihany.cloudmusic.personal.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentUserPlaylistBinding
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.personal.adapter.UserPlaylistAdapter
import com.zihany.cloudmusic.personal.bean.PlayListItemBean
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel

class UserPlayListFragment: BaseFragment<PersonalViewModel>() {
    companion object {
        const val TAG = "UserPlayListFragment"
    }

    private var uid = -1
    private lateinit var adapter: UserPlaylistAdapter
    private var nickname = ""
    private var playListBeans = ArrayList<UserPlayListBean.PlayListBean>()
    private var playListFragmentBeans = ArrayList<PlayListItemBean>()
    private lateinit var binding: FragmentUserPlaylistBinding
    private val listener = object : UserPlaylistAdapter.OnPlayListItemClickListener {
        override fun onPlayListItemClick(position: Int) {
            val intent = Intent(context!!, PlayListActivity::class.java)
            intent.putExtra(WowFragment.PLAYLIST_PICURL, playListBeans[position].coverImgUrl)
            intent.putExtra(WowFragment.PLAYLIST_NAME, playListBeans[position].name)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playListBeans[position].creator?.nickname)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, playListBeans[position].creator?.avatarUrl)
            intent.putExtra(WowFragment.PLAYLIST_ID, playListBeans[position].id)
            startActivity(intent)
        }

        override fun onSmartPlayClick(position: Int) {
            TODO("Not yet implemented")
        }

    }

    init {
        fragmentTitle = "音乐"
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        playListBeans.clear()
        playListFragmentBeans.clear()

        adapter = UserPlaylistAdapter(context!!)
        adapter.nickName = nickname
        binding.rv.layoutManager = LinearLayoutManager(context!!)
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter

        if (uid != -1) {
            showDialog()

        }
    }
}