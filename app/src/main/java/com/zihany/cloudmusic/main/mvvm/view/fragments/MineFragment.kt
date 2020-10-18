package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentMineBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.mvvm.view.LocalMusicActivity
import com.zihany.cloudmusic.main.mvvm.view.MySubActivity
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.MineViewModel
import com.zihany.cloudmusic.personal.adapter.UserPlaylistAdapter
import com.zihany.cloudmusic.personal.bean.PlayListItemBean
import com.zihany.cloudmusic.personal.bean.Playlist
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ToastUtils
import com.zihany.cloudmusic.util.isFastClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class MineFragment : BaseFragment<FragmentMineBinding>(R.layout.fragment_mine) {
    private val viewModel by viewModel<MineViewModel>()
    private val adapterList = ArrayList<PlayListItemBean>()
    private val playlistBeans = ArrayList<Playlist>()
    private val adapter by lazy { UserPlaylistAdapter(context!!) }

    override fun initView() {
        binding.apply {
            rlFm.setOnClickListener {
                onClick(it)
            }

            localMusic.setOnClickListener {
                onClick(it)
            }

            myCollection.setOnClickListener {
                onClick(it)
            }

            adapter = this@MineFragment.adapter
        }

        //showDialog()
    }

    override fun startObserve() {
        viewModel.apply {
//            loginBean.observe(this@MineFragment, Observer<LoginBean> {
//                adapter.nickName = it.account?.userName
//            })


            userPlayListBean.observe(viewLifecycleOwner, Observer {
                LogUtil.d(TAG, "change userPlayList")
                val playListBean = it.playlist
                playlistBeans.clear()
                playlistBeans.addAll(playListBean)
                for (bean: Playlist in playListBean) {
                    val beanInfo = PlayListItemBean()
                    beanInfo.coverUrl = bean.coverImgUrl
                    beanInfo.playListId = bean.id
                    beanInfo.playCount = bean.playCount
                    beanInfo.playListName = bean.name
                    beanInfo.songNumber = bean.trackCount
                    beanInfo.playListCreator = bean.creator.nickname
                    this@MineFragment.adapterList.add(beanInfo)
                }
                adapter.notifyDataSetChanged(this@MineFragment.adapterList)
            })

            getUserPlaylistError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                LogUtil.d(TAG, "onGetUserPlaylistFail: $it")
                ToastUtils.show(it)
            })

            getIntelligenceBeanError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                ToastUtils.show(it)
            })
        }
    }

    init {
        fragmentTitle = "我的"

    }

    override fun initData() {
        adapter.run {
            listener = object : UserPlaylistAdapter.OnPlayListItemClickListener {
                override fun onPlayListItemClick(position: Int) {
                    val intent = Intent(context, PlayListActivity::class.java)
                    intent.putExtra(WowFragment.PLAYLIST_PICURL, playlistBeans[position].coverImgUrl)
                    intent.putExtra(WowFragment.PLAYLIST_NAME, playlistBeans[position].name)
                    intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, playlistBeans[position].creator!!.nickname)
                    intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, playlistBeans[position].creator!!.avatarUrl)
                    intent.putExtra(WowFragment.PLAYLIST_ID, playlistBeans[position].id)
                    context?.startActivity(intent)
                }

                override fun onSmartPlayClick(position: Int) {
                    showDialog()
                    viewModel.getIntelligenceList(1, playlistBeans[position].id)
                }

            }

            isShowSmartPlay = true
            nickName = viewModel.loginBean!!.account.userName
        }
        //showDialog()
        viewModel.getUserPlaylist()
    }

    override fun onClick(view: View) {
        if (view.isFastClick(1000)) {
            return
        }
        val intent = Intent()
        when(view.id) {
            R.id.rl_fm -> {
                showDialog()
                viewModel.getMyFM()
            }
            R.id.local_music -> {
                intent.setClass(context!!, LocalMusicActivity::class.java)
                startActivity(intent)
            }
            R.id.my_collection -> {
                intent.setClass(context!!, MySubActivity::class.java)
                startActivity(intent)
            }
        }
    }

}

