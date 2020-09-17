package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentMineBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.MyFmBean
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.MineViewModel
import com.zihany.cloudmusic.manager.SongPlayManager
import com.zihany.cloudmusic.personal.adapter.UserPlaylistAdapter
import com.zihany.cloudmusic.personal.bean.PlayListItemBean
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.SharePreferenceUtil
import com.zihany.cloudmusic.util.ToastUtils

class MineFragment : BaseFragment<MineViewModel>() {
    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: UserPlaylistAdapter
    private val adapterList = ArrayList<PlayListItemBean>()
    private val listener = object : UserPlaylistAdapter.OnPlayListItemClickListener {
        override fun onPlayListItemClick(position: Int) {
            val intent = Intent(context, PlayListActivity::class.java)
            intent.putExtra(WowFragment.PLAYLIST_PICURL, viewModel.playListBeans.value!![position].coverImgUrl)
            intent.putExtra(WowFragment.PLAYLIST_NAME, viewModel.playListBeans.value!![position].name)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, viewModel.playListBeans.value!![position].creator!!.nickname)
            intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, viewModel.playListBeans.value!![position].creator!!.avatarUrl)
            intent.putExtra(WowFragment.PLAYLIST_ID, viewModel.playListBeans.value!![position].id)
            context?.startActivity(intent)
        }

        override fun onSmartPlayClick(position: Int) {
            showDialog()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MineViewModel::class.java]
        viewModel.apply {
            loginBean.observe(this@MineFragment, Observer<LoginBean> {
                adapter.nickName = it.account?.userName
            })

            playListBeans.observe(this@MineFragment, Observer<ArrayList<UserPlayListBean.PlayListBean>> {
                for (bean: UserPlayListBean.PlayListBean in it) {
                    val beanInfo = PlayListItemBean()
                    beanInfo.coverUrl = bean.coverImgUrl
                    beanInfo.playListId = bean.id
                    beanInfo.playCount = bean.playCount
                    beanInfo.playListName = bean.name
                    beanInfo.songNumber = bean.trackCount
                    beanInfo.playListCreator = bean.creator?.nickname
                    adapterList.add(beanInfo)
                }
                adapter.notifyDataSetChanged(adapterList)
            })

            getUserPlaylistError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                LogUtil.d(TAG, "onGetUserPlaylistFail: + $it")
                this@MineFragment.context?.let { it1 -> ToastUtils.show(it1, it) }
            })

            getIntelligenceBeanError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                this@MineFragment.context?.let { it1 -> ToastUtils.show(it1, it) }
            })

            getMyFMError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                this@MineFragment.context?.let { it1 -> ToastUtils.show(it1, it) }
            })

            getAlbumSublistError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                this@MineFragment.context?.let { it1 -> ToastUtils.show(it1, it) }
            })

            getArtistSublistError.observe(this@MineFragment, Observer<String> {
                hideDialog()
                this@MineFragment.context?.let { it1 -> ToastUtils.show(it1, it) }
            })

            myFM.observe(this@MineFragment, Observer<MyFmBean> {
                hideDialog()
                val fmList = it.data
                val songList = ArrayList<SongInfo>()
                for (bean: MyFmBean.DataBean in fmList!!) {
                    val songInfo = SongInfo()
                    songInfo.songName = bean.name
                    songInfo.songUrl = SONG_URL + bean.id + ".mp3"
                    songInfo.songCover = bean.album?.blurPicUrl
                    songInfo.artist = bean.artists!![0].name
                    songInfo.songId = bean.id.toString()
                    songInfo.duration = bean.duration
                    songInfo.artistId = bean.artists!![0].id.toString()
                    songInfo.artistKey = bean.artists!![0].picUrl
                    songList.add(songInfo)
                }
                SongPlayManager.instance
            })
        }
    }

    init {
        fragmentTitle = "我的"

    }

    override fun initVariables(bundle: Bundle) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        adapter = UserPlaylistAdapter(context!!)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initData() {
        binding?.rvMinePlaylist?.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
        adapter.listener = this.listener
        adapter.isShowSmartPlay = true

        showDialog()

        viewModel.initData(context!!)
        //viewmodel.getUserPlaylist()
    }

}

