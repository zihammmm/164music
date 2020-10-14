package com.zihany.cloudmusic.main.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.main.bean.Banner
import com.zihany.cloudmusic.main.bean.BannerBean
import com.zihany.cloudmusic.main.bean.MainRecommendPlayListBean
import com.zihany.cloudmusic.main.mvvm.model.WowRepository
import com.zihany.cloudmusic.util.LogUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WowViewModel(
        private val wowRepository: WowRepository
): BaseViewModel() {
    companion object {
        const val TAG = "WowViewModel"
    }
    val recommends = MutableLiveData<MainRecommendPlayListBean>()
    val bannerBean = MutableLiveData<BannerBean>()

    val getBannerError = MutableLiveData<String>()
    val getRecommendPlayListError = MutableLiveData<String>()

    fun getBanner() {
        wowRepository.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BannerBean> {
                    override fun onComplete() {
                        LogUtil.d(TAG, "getBanner: onComplete")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "getBanner: onSubscribe")
                    }

                    override fun onNext(t: BannerBean?) {
                        LogUtil.d(TAG, "getBanner: onNext")

                        bannerBean.postValue(t)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "getBanner: onError")
                        getBannerError.postValue(e?.message)
                    }

                })
    }

    fun getRecommendPlayList() {
        wowRepository.getRecommendPlayList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MainRecommendPlayListBean> {
                    override fun onComplete() {
                        LogUtil.d(TAG, "getRecommendPlayList: onComplete")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "getRecommendPlayList: onSubscribe")
                    }

                    override fun onNext(t: MainRecommendPlayListBean?) {
                        LogUtil.d(TAG, "getRecommendPlayList: onNext")
                        recommends.postValue(t)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "getRecommendPlayList: onError")
                        getRecommendPlayListError.postValue(e?.message)
                    }

                })
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