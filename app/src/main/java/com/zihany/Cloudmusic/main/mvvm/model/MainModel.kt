package com.zihany.Cloudmusic.main.mvvm.model

import android.content.Context
import com.lzx.starrysky.model.SongInfo
import com.zihany.Cloudmusic.api.ApiEngine
import com.zihany.Cloudmusic.base.BaseFragment
import com.zihany.Cloudmusic.base.BaseModel
import com.zihany.Cloudmusic.databinding.ActivityMainBinding
import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.Cloudmusic.main.bean.LikeListBean
import com.zihany.Cloudmusic.main.bean.LogoutBean
import com.zihany.Cloudmusic.main.mvvm.viewmodel.MainViewModel
import com.zihany.Cloudmusic.util.GsonUtil
import com.zihany.Cloudmusic.util.SharePreferenceUtil
import io.reactivex.rxjava3.core.Observable

class MainModel : BaseModel {

    fun logout(): Observable<LogoutBean> = ApiEngine.instance.getApiService().logout()

    fun getLikeList(uid: Long): Observable<LikeListBean> = ApiEngine.instance.getApiService().getLikeList(uid)
}