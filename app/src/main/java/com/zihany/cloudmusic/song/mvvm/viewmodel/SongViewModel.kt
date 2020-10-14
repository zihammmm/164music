package com.zihany.cloudmusic.song.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.api.ApiEngine
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.song.bean.LyricBean
import com.zihany.cloudmusic.song.bean.SongDetailBean
import com.zihany.cloudmusic.song.mvvm.model.SongRepository
import com.zihany.cloudmusic.util.LogUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SongViewModel(
        private val songRepository: SongRepository
) : BaseViewModel() {
    companion object {
        const val TAG = "SongViewModel"
    }

    val songDetail = MutableLiveData<SongDetailBean>()
    val getSongDetailError = MutableLiveData<String>()

    fun getSongDetail(ids: Long) {
        songRepository.getSongDetail(ids).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    LogUtil.d(TAG, "onNext: $it")
                    songDetail.postValue(it)
                }, {
                    LogUtil.d(TAG, "onError:${it.message}")
                    getSongDetailError.postValue(it.message)
                })
    }

    val lyricBean = MutableLiveData<LyricBean>()
    val getLyricError = MutableLiveData<String>()

    fun getLyric(id: Long) {
        songRepository.getLyric(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    LogUtil.d(TAG, "onNext:$it")
                    lyricBean.postValue(it)
                }, {
                    LogUtil.d(TAG, "onError:${it.message}")
                    getLyricError.postValue(it.message)
                })
    }
}