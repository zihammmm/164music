package com.zihany.cloudmusic.main.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.*
import com.zihany.cloudmusic.databinding.ActivityMainBinding
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.main.bean.LikeListBean
import com.zihany.cloudmusic.main.mvvm.view.fragments.CloudVillageFragment
import com.zihany.cloudmusic.main.mvvm.view.fragments.MineFragment
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
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

    val pagerAdapter by lazy { MultiFragmentPagerAdapter(supportFragmentManager) }
    private val mineFragment by lazy { MineFragment() }
    private val wowFragment by lazy { WowFragment() }
    private val cloudVillageFragment by lazy { CloudVillageFragment() }
    val fragments: MutableList<BaseFragment<*>> = ArrayList()
    private var firstTime = 0L

    private val viewModel by viewModel<MainViewModel>()
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    private var likeList by PreferenceUtils(LIKE_LIST, "")
    private val authToken by PreferenceUtils(AUTH_TOKEN, "")


    init {
        fragments.add(mineFragment)
        fragments.add(wowFragment)
        fragments.add(cloudVillageFragment)
    }

    override fun onClick(view: View) {
        if (ClickUtil.isFastClick(1000, view)) {
            return
        }
        val intent = Intent()
        when (view.id) {
            R.id.ic_nav -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.rl_logout -> {
                showDialog()
            }
            R.id.rl_avatar_name -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                intent.setClass(this, PersonalInfoActivity::class.java)
                intent.putExtra(LOGIN_BEAN, GsonUtil.toJson(viewModel.loginBean.value!!.profile))
                startActivity(intent)
            }
            R.id.iv_search -> {
                intent.setClass(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun initView() {
        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
        binding.apply {
            viewModel = this@MainActivity.viewModel
            contentMain.ma = this@MainActivity

            contentMain.icNav.setOnClickListener {
                onClick(it)
            }

            rlLogout.setOnClickListener {
                onClick(it)
            }

            rlAvatarName.setOnClickListener {
                onClick(it)
            }

            contentMain.ivSearch.setOnClickListener {
                onClick(it)
            }
        }
    }

    override fun startObserve() {
        viewModel.apply {
            loginBean.observe(this@MainActivity, Observer {
                it.profile.avatarUrl.let { url ->
                    Glide.with(this@MainActivity)
                            .load(url)
                            .into(binding.ivAvatar)
                }
                it.profile.nickname.let { string ->
                    binding.tvUsername.text = string
                }
            })

            likeListBean.observe(this@MainActivity, Observer {
                onGetLikeListSuccess(it)
            })

            getLikeListError.observe(this@MainActivity, Observer {
                it.message?.let { it1 ->
                    onGetLikeListFail(it1)
                }
            })

            logoutError.observe(this@MainActivity, Observer {
                it.message?.let { it1 ->
                    onLogoutFail(it1)
                }
            })

            logoutBean.observe(this@MainActivity, Observer {
                onLogoutSuccess()
            })
        }
    }

    override fun initData() {
        LogUtil.d(TAG, "initData")
        pagerAdapter.init(fragments)
        pagerAdapter.getItem(1).userVisibleHint = true
        connectMusicService()
        binding.contentMain.tabTitle.getTabAt(1)?.let {
            setSelectTextBoldAndBig(it)
        }
        initTabListener()
        viewModel.getLikeList()
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
        PreferenceUtils(AUTH_TOKEN, "").remove(AUTH_TOKEN)
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
        this.likeList = GsonUtil.toJson(likeList)
        LogUtil.d(TAG, "likeList: ${this.likeList}")
    }


}
