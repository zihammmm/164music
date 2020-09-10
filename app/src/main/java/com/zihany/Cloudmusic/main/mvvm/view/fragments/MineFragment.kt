package com.zihany.Cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.Cloudmusic.base.BaseFragment
import com.zihany.Cloudmusic.databinding.FragmentMineBinding
import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.Cloudmusic.personal.adapter.UserPlaylistAdapter
import com.zihany.Cloudmusic.personal.bean.PlayListItemBean
import com.zihany.Cloudmusic.personal.bean.UserPlayListBean
import com.zihany.Cloudmusic.util.GsonUtil
import com.zihany.Cloudmusic.util.SharePreferenceUtil

class MineFragment : BaseFragment() {
    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding
    private var uid = 0L
    private var loginBean: LoginBean? = null
    private var adapter: UserPlaylistAdapter? = null
    private var playListBeans: MutableList<UserPlayListBean.PlayListBean> = ArrayList()
    private var adapterList: MutableList<PlayListItemBean> = ArrayList()

    private val listener = object : UserPlaylistAdapter.OnPlayListItemClickListener {
        override fun onPlayListItemClick(position: Int) {
            val intent = Intent(context, PlayListActivity::class.java)
            intent.putExtra(WowFragment.PLAYLIST_PICURL, playListBeans[position].coverImgUrl)
            intent.putExtra(WowFragment.PLAYLIST_NAME, playListBeans[position].name)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playListBeans[position].creator!!.nickname)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, playListBeans[position].creator!!.avatarUrl)
            intent.putExtra(WowFragment.PLAYLIST_ID, playListBeans[position].id)
            context?.startActivity(intent)
        }

        override fun onSmartPlayClick(position: Int) {
            showDialog()
        }

    }

    init {
        fragmentTitle = "我的"
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showDialog() {
        diaLog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideDialog() {
        diaLog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    fun refreshData() {
        if (isFragmentVisible()) {
            initData()
        } else {
            setForceLoad(true)
        }
    }

    override fun initData() {
        loginBean = GsonUtil.fromJSON<LoginBean>(SharePreferenceUtil.getInstance(context!!).getUserInfo(""))
        uid = loginBean!!.account!!.id

        adapter = UserPlaylistAdapter(context!!)
        binding?.rvMinePlaylist?.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        adapter.listener = this
    }


}