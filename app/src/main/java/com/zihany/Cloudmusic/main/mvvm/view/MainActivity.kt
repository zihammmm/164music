package com.zihany.Cloudmusic.main.mvvm.view

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.TextView
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
import com.zihany.Cloudmusic.databinding.ActivityMainBinding
import com.zihany.Cloudmusic.login.bean.LoginBean
import com.zihany.Cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.Cloudmusic.main.mvvm.view.fragments.MineFragment
import com.zihany.Cloudmusic.main.mvvm.viewmodel.MainViewModel
import com.zihany.Cloudmusic.widget.BottomSongPlayBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {
    companion object {
        private val TAG = "MainActivity"
        private val VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2
        val LOGIN_BEAN = "loginBean"
    }
    private var viewModel = MainViewModel()
    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    private var pagerAdapter: MultiFragmentPagerAdapter? = null
    private val fragments: MutableList<BaseFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.userName.observe(this, Observer<String> {
            binding.tvUsername.text = it
        })
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(binding.root)

        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
        connectMusicService()

        pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
        fragments.add(MineFragment())
    }

    override fun initData() {
        binding.
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

}
