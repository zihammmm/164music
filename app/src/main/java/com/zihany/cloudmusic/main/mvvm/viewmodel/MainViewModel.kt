package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData

import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.bean.LogoutBean
import com.zihany.cloudmusic.main.mvvm.model.MainModel
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.SharePreferenceUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainViewModel : BaseViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    var loginBean: MutableLiveData<LoginBean> = MutableLiveData()
    var logoutBean: MutableLiveData<LogoutBean> = MutableLiveData()
    var likeListBean: MutableLiveData<LikeListBean> = MutableLiveData()
    var getLikeListError: MutableLiveData<Throwable> = MutableLiveData()
    var logoutError: MutableLiveData<Throwable> = MutableLiveData()
    private val model = MainModel()

    override fun initData(context: Context) {
        val userLoginInfo = SharePreferenceUtil.getInstance(context).getUserInfo("")
        loginBean.value = GsonUtil.fromJSON<LoginBean>(userLoginInfo)

    }

    fun getLikeList() {
        model.getLikeList(loginBean.value!!.account!!.id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<LikeListBean> {
                        override fun onComplete() {
                            LogUtil.d(TAG, "onComplete")
                        }

                        override fun onSubscribe(d: Disposable?) {
                            LogUtil.d(TAG, "onSubscribe")
                        }

                        override fun onNext(t: LikeListBean?) {
                            LogUtil.d(TAG, "onNext")
                            likeListBean.value = t
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.e(TAG, "onError: $e")
                            getLikeListError.value = e
                        }

                    }
                }
    }

    fun logout() {
        model.logout().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LogoutBean> {
                    override fun onComplete() {
                        LogUtil.d(TAG, "onComplete")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "onSubscribe")
                    }

                    override fun onNext(t: LogoutBean?) {
                        LogUtil.d(TAG, "onNext: $t")
                        logoutBean.value = t
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "onError: $e")
                        logoutError.value = e
                    }

                })
    }

}