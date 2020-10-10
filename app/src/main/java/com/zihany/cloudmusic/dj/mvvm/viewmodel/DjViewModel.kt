package com.zihany.cloudmusic.dj.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zihany.cloudmusic.base.BaseViewModel
import com.zihany.cloudmusic.dj.bean.DjPayGiftBean

class DjViewModel: BaseViewModel() {
    companion object {
        const val TAG = "DjViewModel"
    }
    val djPayGiftBean = MutableLiveData<DjPayGiftBean>()


    fun getDjRecommend() {

    }

    fun getDjPayGift(limit: Int, offset: Int) {

    }

    fun getDjRecommendType(type: Int) {

    }

    fun getDjCategoryRecommend() {

    }

    fun getDjCateList() {

    }

    fun subDj(rid: Long, isSub: Int) {

    }

    fun getDjProgram(rid: Long) {

    }

    fun getDjDetail(rid: Long) {

    }
}