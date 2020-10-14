package com.zihany.cloudmusic.main.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.main.bean.PlaylistDetailBean
import com.zihany.cloudmusic.main.mvvm.model.PlayListRepository
import com.zihany.cloudmusic.util.LogUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PlayListViewModel(
        private val playListRepository: PlayListRepository
): BaseViewModel() {
    companion object {
        const val TAG = "PlayListViewModel"
    }

    val playlistPicUrl = ObservableField<String>("")
    val playlistName = ObservableField<String>("")
    val creatorName = ObservableField<String>("")
    val creatorUrl = ObservableField<String>("")
    val playlistId = ObservableField<Long>(0L)

    val bean = MutableLiveData<PlaylistDetailBean>()
    val error = MutableLiveData<String>()

    fun getPlayListDetail() {
        playlistId.get()?.let {
            playListRepository.getPlayListDetail(it).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<PlaylistDetailBean> {
                    override fun onSubscribe(d: Disposable?) {
                        LogUtil.d(TAG, "onSubscribe")
                    }

                    override fun onNext(t: PlaylistDetailBean?) {
                        LogUtil.d(TAG, "onNext: $t")
                        bean.postValue(t)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "onError: ${e?.message}")
                        error.postValue(e?.message)
                    }

                    override fun onComplete() {
                        LogUtil.d(TAG, "onComplete")
                    }

                })
        }
    }

}