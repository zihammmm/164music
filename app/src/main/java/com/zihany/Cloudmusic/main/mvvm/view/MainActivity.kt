package com.zihany.Cloudmusic.main.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.zihany.Cloudmusic.R
import com.zihany.Cloudmusic.base.BaseActivity
import com.zihany.Cloudmusic.base.BaseFragment
import com.zihany.Cloudmusic.base.BaseView
import com.zihany.Cloudmusic.databinding.ActivityMainBinding
import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.Cloudmusic.main.mvvm.view.fragments.MineFragment
import com.zihany.Cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.Cloudmusic.main.mvvm.viewmodel.MainViewModel
import com.zihany.Cloudmusic.util.ClickUtil
import com.zihany.Cloudmusic.widget.BottomSongPlayBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BaseView {
    companion object {
        private val TAG = "MainActivity"
        private val VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2
        val LOGIN_BEAN = "loginBean"
    }

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.userName.observe(this, Observer<String> {
            binding.tvUsername.text = it
        })
    }

    override fun initData() {

        binding.contentMain.tabTitle.setupWithViewPager(binding.contentMain.mainViewpager)
        binding.contentMain.tabTitle.setTabTextColors(Color.parseColor("#e78c86"),
                Color.parseColor("#FFFDFD"))
        initView(viewModel.loginBean)
    }

    @SuppressLint("InflateParams")
    private fun setSelectTextBoldAndBig(tab: TabLayout.Tab) {
        val textview: TextView
                = LayoutInflater.from(context).inflate(R.layout.design_layout_tab_text, null) as TextView
        textview.text = tab.text
        textview.scaleX = 1.5f
        textview.scaleY = 1.5f
        textview.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        textview.setTextColor(Color.parseColor("#FFFDFD"))
        tab.customView = textview
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(binding.root)

        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
        connectMusicService()

        viewModel.pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
        viewModel.fragments.add(MineFragment())
        viewModel.fragments.add(WowFragment())
    }



    private fun initView(bean: LoginBean) {
        bean.profile!!.avatarUrl?.let {
            Glide.with(this)
                    .load(it)
                    .into(iv_avatar)
        }
        bean.profile!!.nickname?.let {
            viewModel.userName.value = it
        }
    }

    override fun onClick(v: View?) {
        if (ClickUtil.isFastClick(1000, v)) {
            return
        }
        val intent = Intent()
        when(v!!.id) {
            R.id.ic_nav -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.rl_logout -> {
                showDialog()
            }
            R.id.rl_avatar_name -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                intent.setClass(this, )
            }
        }
    }


}
