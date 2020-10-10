package com.zihany.cloudmusic.main.mvvm.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.bean.PlayModeIntelligenceBean
import com.zihany.cloudmusic.personal.bean.UserPlayListBean
import com.zihany.cloudmusic.util.GsonUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.zihany.cloudmusic.base.Result
import com.zihany.cloudmusic.base.USER_INFO
import com.zihany.cloudmusic.main.mvvm.model.IntelligenceListRepository
import com.zihany.cloudmusic.main.mvvm.model.UserPlayListRepository
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.PreferenceUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MineViewModel(
        private val userPlayListRepository: UserPlayListRepository,
        private val intelligenceListRepository: IntelligenceListRepository
) : BaseViewModel() {

    companion object {
        const val TAG = "MineViewModel"
    }

    var uid = 0L

    val userInfo by PreferenceUtils(USER_INFO, "")
    val userPlayListBean = MutableLiveData<UserPlayListBean>()
    val intelligenceList = MutableLiveData<PlayModeIntelligenceBean>()

    val getUserPlaylistError = MutableLiveData<String>()
    val getIntelligenceBeanError = MutableLiveData<String>()
    val loginBean = GsonUtil.instance.fromJson<LoginBean>(userInfo, LoginBean::class.java)

    fun getUserPlaylist() {
        LogUtil.d(TAG, "getUserPlaylist")
        uid = loginBean!!.account.id
        userPlayListRepository.getUserPlaylist(uid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<UserPlayListBean> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onNext(t: UserPlayListBean?) {
                        LogUtil.d(TAG, "userPlayList: $t")
                        userPlayListBean.postValue(t)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.d(TAG, "onError: ${e?.message}")
                    }

                    override fun onComplete() {

                    }

                })
    }

    fun getIntelligenceList(id: Long, pid: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = intelligenceListRepository.getIntelligenceList(id, pid)
//            if (result is Result.Success) {
//                intelligenceList.value = result.data
//            } else if (result is Result.Error) {
//                getIntelligenceBeanError.value = result.exception.message
//            }
//        }
        intelligenceListRepository.getIntelligenceList(id, pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<PlayModeIntelligenceBean> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onNext(t: PlayModeIntelligenceBean?) {
                        LogUtil.d(TAG, "intelligence: $t")
                    }

                    override fun onError(e: Throwable?) {

                    }

                    override fun onComplete() {

                    }

                })
    }

    fun getMyFM() {

    }

}