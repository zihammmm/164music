package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentWowBinding
import com.zihany.cloudmusic.dj.mvvm.view.RadioRecommendActivity
import com.zihany.cloudmusic.main.adapter.PlayListAdapter
import com.zihany.cloudmusic.main.bean.*
import com.zihany.cloudmusic.main.mvvm.view.DailyRecommendActivity
import com.zihany.cloudmusic.main.mvvm.view.PlayListActivity
import com.zihany.cloudmusic.main.mvvm.view.PlayListRecommendActivity
import com.zihany.cloudmusic.main.mvvm.view.RankActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.util.BannerGlideImageAdapter
import com.zihany.cloudmusic.util.ClickUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ToastUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class WowFragment
    : BaseFragment<FragmentWowBinding>(R.layout.fragment_wow) {
    companion object {
        const val TAG = "WowFragment"
        const val PLAYLIST_NAME = "playlistName"
        const val PLAYLIST_PICURL = "playlistPicUrl"
        const val PLAYLIST_CREATOR_NICKNAME = "playlistCreatorNickname"
        const val PLAYLIST_CREATOR_AVATARURL = "playlistCreatorAvatarUrl"
        const val PLAYLIST_ID = "playlistId"
    }

    private val banners = ArrayList<Banner>()
    private lateinit var recommendPlayListAdapter: PlayListAdapter
    private val list = ArrayList<PlaylistBean>()
    private val recommends = ArrayList<Recommend>()
    private val viewModel by viewModel<WowViewModel>()

    private val listClickListener: PlayListAdapter.OnPlayListClickListener = object : PlayListAdapter.OnPlayListClickListener {
        override fun onClickListener(position: Int) {
            if (recommends.isNotEmpty()) {
                val intent = Intent(activity, PlayListActivity::class.java)
                val bean = recommends[position]
                intent.putExtra(PLAYLIST_NAME, bean.name)
                intent.putExtra(PLAYLIST_PICURL, bean.picUrl)
                intent.putExtra(PLAYLIST_CREATOR_NICKNAME, bean.creator.nickname)
                intent.putExtra(PLAYLIST_CREATOR_AVATARURL, bean.creator.nickname)
                intent.putExtra(PLAYLIST_ID, bean.id)
                startActivity(intent)
            }
        }

    }

    init {
        fragmentTitle = "发现"
    }

    private fun onGetBannerSuccess(bean: BannerBean) {
        LogUtil.d(TAG, "onGetBannerSuccess $bean")
        banners.addAll(bean.banners)
        binding.wowBanner.setIndicator(CircleIndicator(context))
                .setAdapter(BannerGlideImageAdapter(bean))
                .addBannerLifecycleObserver(this)
                .isAutoLoop(true)
                .start()
    }

    inner class WowPresenter {
        fun onClickRlDayRec(v: View) {
            LogUtil.d(TAG, "onClickRlDayRec")
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, DailyRecommendActivity::class.java))
        }

        fun onClickRlPlayList(v: View) {
            LogUtil.d(TAG, "onClickRlPlayList")
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, PlayListRecommendActivity::class.java))
        }

        fun onClickRlRank(v: View) {
            LogUtil.d(TAG, "onClickRlRank")
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, RankActivity::class.java))
        }

        fun onClickRlRadio(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, RadioRecommendActivity::class.java))
        }

        fun onClickRlLive(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            ToastUtils.show("无服务")
        }

        fun onClickPlaylistPlayground(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, PlayListRecommendActivity::class.java))
        }

    }

    override fun initData() {
        LogUtil.d(TAG, "initData")
        recommendPlayListAdapter = PlayListAdapter(context!!)
        recommendPlayListAdapter.type = 1
        val manager = GridLayoutManager(context, 3)
        binding.rvRecommendPlaylist.layoutManager = manager
        binding.rvRecommendPlaylist.setHasFixedSize(true)
        binding.rvRecommendPlaylist.adapter = recommendPlayListAdapter
//        showDialog()
        viewModel.getBanner()
        viewModel.getRecommendPlayList()
    }

    private fun onGetBannerFail(msg: String) {
        ToastUtils.show(msg)
        LogUtil.e(TAG, "onGetBannerFail: $msg")
    }

    private fun onGetRecommendPlayListSuccess(bean: MainRecommendPlayListBean) {
        hideDialog()
        LogUtil.d(TAG, "onGetRecommendPlayListSuccess $bean")
        recommends.addAll(bean.recommend)
        for (beanInfo: Recommend in recommends) {
            val playlistBean = PlaylistBean()
            playlistBean.playlistName = beanInfo.name
            playlistBean.playlistCoverUrl = beanInfo.picUrl
            list.add(playlistBean)
        }
        recommendPlayListAdapter.listener = listClickListener
        recommendPlayListAdapter.notifyDataSetChanged(list)
    }

    private fun onGetRecommendPlayListFail(msg: String) {
        hideDialog()
        LogUtil.e(TAG, "onGetRecommendPlayListFail: $msg")
    }

    private fun onGetDailyRecommendSuccess() {

    }

    private fun onGetDailyRecommendFail(msg: String) {

    }

    private fun onGetTopListSuccess() {

    }

    private fun onGetTopListFail(msg: String) {

    }

    private fun onGetPlayListSuccess(type: String, limit: Int) {

    }

    private fun onGetPlayListFail(msg: String) {

    }

    private fun onGetPlaylistDetailSuccess(id: Long) {

    }

    private fun onGetPlaylistDetailFail(msg: String) {

    }

    private fun onGetMusicCanPlaySuccess(id: Long) {

    }

    private fun onGetMusicCanPlayFail(msg: String) {

    }

    private fun onGetHighQualitySuccess(limit: Int, before: Long) {

    }

    private fun onGetHighQualityFail(msg: String) {

    }

    override fun initView() {

    }

    override fun startObserve() {
        viewModel.apply {
            bannerBean.observe(viewLifecycleOwner, Observer {
                onGetBannerSuccess(it)
            })

            getBannerError.observe(viewLifecycleOwner, Observer {
                onGetBannerFail(it)
            })

            recommends.observe(viewLifecycleOwner, Observer {
                onGetRecommendPlayListSuccess(it)
            })

            getRecommendPlayListError.observe(viewLifecycleOwner, Observer {
                onGetRecommendPlayListFail(it)
            })
        }
    }

    override fun onClick(view: View) {

    }

}