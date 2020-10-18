package com.zihany.cloudmusic.song.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.manager.SongPlayManager
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
    val playState = MutableLiveData<PlayState>(PlayState.Paused)

    enum class PlayState{
        Playing, Paused
    }

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

    fun pauseMusic() {
        LogUtil.d(TAG, "pauseMusic")
        SongPlayManager.instance.pauseMusic()
        playState.value = PlayState.Paused
    }

    fun playMusic() {
        LogUtil.d(TAG, "playMusic")
        SongPlayManager.instance.playMusic()
        playState.value = PlayState.Playing
    }

    fun playASong(songInfo: SongInfo) {
        SongPlayManager.instance.clickASong(songInfo)
        playState.value = PlayState.Playing
    }
}