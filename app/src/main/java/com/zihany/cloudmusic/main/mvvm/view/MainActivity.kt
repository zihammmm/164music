package com.zihany.cloudmusic.main.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.base.Constants
import com.zihany.cloudmusic.databinding.ActivityMainBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.mvvm.viewmodel.MainViewModel
import com.zihany.cloudmusic.personal.mvvm.view.PersonalInfoActivity
import com.zihany.cloudmusic.search.mvvm.view.SearchActivity
import com.zihany.cloudmusic.util.*
import kotlin.system.exitProcess
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2
        const val LOGIN_BEAN = "loginBean"
    }

    val pagerAdapter by lazy { MultiFragmentPagerAdapter() }
    val fragments: MutableList<BaseFragment<*>> = ArrayList()
    var firstTime = 0L

    private val mainViewModel by viewModel<MainViewModel>()
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.d(TAG, "onCreate")
        binding.lifecycleOwner = this
        super.onCreate(savedInstanceState)

//        viewModel.apply {
//            loginBean.observe(this@MainActivity, Observer<LoginBean> {
//                it.profile?.avatarUrl?.let { it1 ->
//                    Glide.with(this@MainActivity)
//                            .load(it1)
//                            .into(binding.ivAvatar)
//                }
//                it.profile?.nickname?.let {
//                    binding.tvUsername.text = it
//                }
//            })
//
//            likeListBean.observe(this@MainActivity, Observer<LikeListBean> {
//                onGetLikeListSuccess(it)
//            })
//
//            getLikeListError.observe(this@MainActivity, Observer<Throwable> {
//                it.message?.let { it1 ->
//                    onGetLikeListFail(it1)
//                }
//            })
//
//            logoutError.observe(this@MainActivity, Observer<Throwable> {
//                it.message?.let { it1 ->
//                    onLogoutFail(it1)
//                }
//            })
//
//            logoutBean.observe(this@MainActivity, Observer<LogoutBean> {
//                onLogoutSuccess()
//            })
//        }
    }


    override fun initView() {
        binding.apply {
            viewModel = mainViewModel
            mainPresenter = MainActivityPresenter()
        }
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        LogUtil.d(TAG, "initData")

        pagerAdapter.getItem(1).userVisibleHint = true
        binding.contentMain.tabTitle.getTabAt(1)?.let {
            setSelectTextBoldAndBig(it)
        }
        initTabListener()
        //viewModel.getLikeList()
    }

    private fun initTabListener() {
        binding.contentMain.tabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = null
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    setSelectTextBoldAndBig(it)
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun setSelectTextBoldAndBig(tab: TabLayout.Tab) {
        val textview = LayoutInflater.from(context).inflate(R.layout.design_layout_tab_text, null) as TextView
        textview.text = tab.text
        textview.scaleX = 1.5f
        textview.scaleY = 1.5f
        textview.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        textview.setTextColor(Color.parseColor("#FFFDFD"))
        tab.customView = textview
    }

    inner class MainActivityPresenter {
        fun onClickNav(view: View?) {
            LogUtil.d(TAG, "onClickNav")
            if (ClickUtil.isFastClick(1000, view)) {
                return
            }
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        fun onClickLogout(view: View?) {
            if (ClickUtil.isFastClick(1000, view)) {
                return
            }
            showDialog()
        }

        fun onClickAvatarName(view: View) {
            if (ClickUtil.isFastClick(1000, view)) {
                return
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            val intent = Intent()
            intent.setClass(this@MainActivity, PersonalInfoActivity::class.java)
            intent.putExtra(LOGIN_BEAN, GsonUtil.toJson(viewModel.loginBean.value!!.profile!!))
            startActivity(intent)
        }

        fun onClickSearch(view: View?) {
            if (ClickUtil.isFastClick(1000, view)) {
                return
            }
            val intent = Intent()
            intent.setClass(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //按下返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            exitApp(2000)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectMusicService()
    }

    private fun exitApp(timeInterval: Long) {
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            ToastUtils.show("再按一次退出应用")
            firstTime = System.currentTimeMillis()
        } else {
            finish()
            exitProcess(0)
        }
    }

    fun onLogoutSuccess() {
        hideDialog()
        SharePreferenceUtil.getInstance(this)
                .remove(Constants.AUTH_TOKEN)
        ActivityStarter.instance
                .startLoginActivity(this)
        this.finish()
    }

    fun onLogoutFail(e: String) {
        hideDialog()
        LogUtil.e(TAG, "onLogoutFail: $e")
        ToastUtils.show(e)
    }

    fun onGetLikeListFail(e: String) {
        LogUtil.d(TAG, "onGetLikeListFail")
        ToastUtils.show(e)
    }

    fun onGetLikeListSuccess(bean: LikeListBean) {
        LogUtil.d(TAG, "onGetLikeListSuccess: $bean")
        val idsList = bean.ids
        val likeList = ArrayList<String>()
        idsList?.let {
            for (i: Long in it) {
                likeList.add(i.toString())
            }
        }
        SharePreferenceUtil.getInstance(this)
                .saveLikeList(likeList)
    }



}
