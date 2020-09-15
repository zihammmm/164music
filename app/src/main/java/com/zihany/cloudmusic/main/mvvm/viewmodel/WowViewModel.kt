package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.main.bean.BannerBean
import com.zihany.cloudmusic.main.bean.MainRecommendPlayListBean
import com.zihany.cloudmusic.main.bean.PlaylistBean

class WowViewModel: BaseViewModel() {
    val list = MutableLiveData<ArrayList<PlaylistBean>>()
    val recommends = MutableLiveData<ArrayList<MainRecommendPlayListBean.RecommendBean>>()
    val banners = MutableLiveData<ArrayList<BannerBean.BannersBean>>()

    override fun initData(context: Context) {
        list.value?.clear()
        recommends.value?.clear()

    }

    fun getBanner() {

    }

    fun getRecommendPlayList() {

    }

    fun getDailyRecommend() {

    }

    fun getTopList() {

    }

    fun getPlayList(type: String, limit: Int) {

    }

    fun getPlaylistDetail(id: Long) {

    }

    fun getMusicCanPlay(id: Long) {

    }

    fun getHighQuality(limit: Int, before: Long) {

    }


}