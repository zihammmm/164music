package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.main.bean.BannerBean
import com.zihany.cloudmusic.main.bean.MainRecommendPlayListBean
import com.zihany.cloudmusic.main.mvvm.model.WowRepository
import com.zihany.cloudmusic.util.LogUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WowViewModel: BaseViewModel() {
    companion object {
        const val TAG = "WowViewModel"
    }
    val recommends = MutableLiveData<MainRecommendPlayListBean>()
    val bannerBean = MutableLiveData<BannerBean>()

    val model = WowRepository()

    val getBannerError = MutableLiveData<String>()
    val getRecommendPlayListError = MutableLiveData<String>()

    override fun initData(context: Context) {

    }

    fun getBanner() {
        model.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { object : Observer<BannerBean> {
                    override fun onComplete() {
                        LogUtil.d(TAG, "getBanner onComplete")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "getBanner onSubscribe")
                    }

                    override fun onNext(t: BannerBean?) {
                        LogUtil.d(TAG, "BannerBean : $t")
                        bannerBean.value = t
                    }

                    override fun onError(e: Throwable?) {
                        getBannerError.value = e?.message
                    }

                } }
    }

    fun getRecommendPlayList() {
        model.getRecommendPlayList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { object : Observer<MainRecommendPlayListBean> {
                    override fun onComplete() {
                        LogUtil.d(TAG, "getRecommendPlayList onSubscribe")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "getRecommendPlayList onSubscribe")
                    }

                    override fun onNext(t: MainRecommendPlayListBean?) {
                        LogUtil.d(TAG, "onNext $t")
                        recommends.value = t
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "onError: $e")
                        getRecommendPlayListError.value = e?.message
                    }

                } }
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