package com.zihany.Cloudmusic

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.zihany.Cloudmusic.widget.BottomSongPlayBar
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity: AppCompatActivity() {
    companion object {
        private val TAG = "MainActivity"
        private val VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2
        val LOGIN_BEAN = "loginBean"
    }

    var ivAvatar: CircleImageView? = null
    var userName: TextView? = null
    var drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var viewPager: ViewPager? = null
    var tabTitle: TabLayout? = null
    var bottomController: BottomSongPlayBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
