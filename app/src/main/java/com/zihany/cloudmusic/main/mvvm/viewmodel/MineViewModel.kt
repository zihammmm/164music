package com.zihany.cloudmusic.main.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseModel
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.AlbumSublistBean
import com.zihany.cloudmusic.main.bean.ArtistSublistBean
import com.zihany.cloudmusic.main.bean.MyFmBean
import com.zihany.cloudmusic.main.bean.PlayModeIntelligenceBean
import com.zihany.cloudmusic.main.mvvm.model.MineModel
import com.zihany.cloudmusic.personal.bean.PlayListItemBean
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.SharePreferenceUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MineViewModel : BaseViewModel() {
    companion object {
        const val TAG = "MineViewModel"
    }

    var uid = 0L
    val loginBean = MutableLiveData<LoginBean>()
    val playListBeans = MutableLiveData<ArrayList<UserPlayListBean.PlayListBean>>()
    val model = MineModel()

    val getUserPlaylistError = MutableLiveData<String>()
    val getIntelligenceBeanError = MutableLiveData<String>()
    var getMyFMError = MutableLiveData<String>()
    val getAlbumSublistError = MutableLiveData<String>()
    val getArtistSublistError = MutableLiveData<String>()

    val myFM = MutableLiveData<MyFmBean>()
    val albumSublist = MutableLiveData<AlbumSublistBean>()
    val artistSublist = MutableLiveData<ArtistSublistBean>()
    val playModeIntelligenceBean = MutableLiveData<PlayModeIntelligenceBean>()

    override fun initData(context: Context) {
        loginBean.value = GsonUtil.fromJSON<LoginBean>(SharePreferenceUtil.getInstance(context).getUserInfo(""))
        uid = loginBean.value!!.account!!.id
    }

    fun getUserPlaylist() {
        model.getUserPlaylist(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<UserPlayListBean> {
                        override fun onSubscribe(d: Disposable?) {
                            LogUtil.d(TAG, "getUserPlaylist onSubscribe")
                        }

                        override fun onNext(t: UserPlayListBean?) {
                            LogUtil.d(TAG, "getUserPlaylist onNext : $t")
                            playListBeans.value?.clear()
                            t?.playList?.let { it1 -> playListBeans.value?.addAll(it1) }
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.e(TAG, "getUserPlaylist onError : $e")
                            getUserPlaylistError.value = e?.message
                        }

                        override fun onComplete() {
                            LogUtil.d(TAG, "getUserPlaylist onComplete")
                        }

                    }
                }
    }

    fun getMyFM() {
        model.getMyFm().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<MyFmBean> {
                        override fun onSubscribe(d: Disposable?) {
                            LogUtil.d(TAG, "getMyFM onSubscribe")
                        }

                        override fun onNext(t: MyFmBean?) {
                            LogUtil.d(TAG, "getMyFM onNext : $t")
                            myFM.value = t
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.d(TAG, "getMyFM onError : ${e?.message}")
                            getMyFMError.value = e?.message
                        }

                        override fun onComplete() {
                            LogUtil.d(TAG, "getMyFM onComplete")
                        }

                    }
                }
    }

    fun getAlbumSublist() {
        model.getAlbumSublistBean().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<AlbumSublistBean> {
                        override fun onSubscribe(d: Disposable?) {
                            LogUtil.d(TAG, "getAlbumSublist")
                        }

                        override fun onNext(t: AlbumSublistBean?) {
                            LogUtil.d(TAG, "getAlbumSublist : $t")
                            albumSublist.value = t
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.d(TAG, "getAlbumSublist OnError : ${e?.message}")
                            getAlbumSublistError.value = e?.message
                        }

                        override fun onComplete() {
                            LogUtil.d(TAG, "getAlbumSublist onComplete")
                        }

                    }
                }
    }

    fun getArtistSublist() {
        model.getArtistSublist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<ArtistSublistBean> {
                        override fun onSubscribe(d: Disposable?) {
                            LogUtil.d(TAG, "getArtistSublist onSubscribe")
                        }

                        override fun onNext(t: ArtistSublistBean?) {
                            LogUtil.d(TAG, "getArtistSublist onNext: $t")
                            artistSublist.value = t
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.d(TAG, "getArtistSublist OnError : ${e?.message}")
                            getArtistSublistError.value = e?.message
                        }

                        override fun onComplete() {
                            LogUtil.d(TAG, "getArtistSublist onComplete")
                        }

                    }
                }
    }

    fun getIntelligenceList(id: Long, pid: Long) {
        model.getIntelligenceList(id, pid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object : Observer<PlayModeIntelligenceBean> {
                        override fun onSubscribe(d: Disposable?) {

                        }

                        override fun onNext(t: PlayModeIntelligenceBean?) {
                            playModeIntelligenceBean.value = t
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.d(TAG, "getIntelligenceList onError : ${e?.message}")
                            getIntelligenceBeanError.value = e?.message
                        }

                        override fun onComplete() {

                        }

                    }
                }
    }
}