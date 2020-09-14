package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.youth.banner.indicator.CircleIndicator
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentWowBinding
import com.zihany.cloudmusic.dj.mvvm.view.RadioRecommendActivity
import com.zihany.cloudmusic.main.bean.BannerBean
import com.zihany.cloudmusic.main.mvvm.view.DailyRecommendActivity
import com.zihany.cloudmusic.main.mvvm.view.PlaylistRecommendActivity
import com.zihany.cloudmusic.main.mvvm.view.RankActivity
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel
import com.zihany.cloudmusic.util.BannerGlideImageAdapter
import com.zihany.cloudmusic.util.ClickUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ToastUtils

class WowFragment : BaseFragment<WowViewModel>() {
    companion object {
        const val TAG = "WowFragment"
        const val PLAYLIST_NAME = "playlistName"
        const val PLAYLIST_PICURL = "playlistPicUrl"
        const val PLAYLIST_CREATOR_NICKNAME = "playlistCreatorNickname"
        const val PLAYLIST_CREATOR_AVATARURL = "playlistCreatorAvatarUrl"
        const val PLAYLIST_ID = "playlistId"
    }

    private lateinit var binding: FragmentWowBinding
    private val banners = ArrayList<BannerBean.BannersBean>()

    init {
        fragmentTitle = "发现"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WowViewModel::class.java]
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        LogUtil.d(TAG, "initView isPrepared:${isPrepared()}, isFragmentVisible:${isFragmentVisible()}")
        binding = FragmentWowBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun onGetBannerSuccess(bean: BannerBean) {
        LogUtil.d(TAG, "onGetBannerSuccess $bean")
        binding.wowBanner.setIndicator(CircleIndicator(context))
                .setAdapter(BannerGlideImageAdapter(bean))
                .addBannerLifecycleObserver(this)
                .start()

    }

    inner class WowPresenter {

        fun onClickRlDayRec(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, DailyRecommendActivity::class.java))
        }

        fun onClickRlPlayList(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, PlaylistRecommendActivity::class.java))
        }

        fun onClickRlRank(v: View) {
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
            ToastUtils.show(this@WowFragment.context!!, "无服务")
        }

        fun onClickPlaylistPlayground(v: View) {
            if (ClickUtil.isFastClick(1000, v)) {
                return
            }
            startActivity(Intent(activity, PlaylistRecommendActivity::class.java))
        }

    }

    override fun initVariables(bundle: Bundle) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        LogUtil.d(TAG, "initData")

    }
}