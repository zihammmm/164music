package com.zihany.cloudmusic.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzx.starrysky.manager.MediaSessionConnection
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.util.LocaleManageUtil
import com.zihany.cloudmusic.widget.LoadingDialog

abstract class BaseActivity<T: BaseViewModel>: AppCompatActivity(){
    companion object {
        val SONG_URL = "http://music.163.com/song/media/outer/url?id="
    }

    private val TAG = "BaseActivity"
    var context: Context? = null
    protected lateinit var viewModel: T
    protected var diaLog: LoadingDialog? = null
    private var bottomSongInfo: SongInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context  = this
        createDialog()
        onCreateView(savedInstanceState)
        initData()
    }
    protected abstract fun initData()
    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleManageUtil.setLocal(it) })
    }

    fun startActivity(target: Class<out AppCompatActivity>, bundle: Bundle?, finish: Boolean) {
        val intent = Intent()
        intent.setClass(this, target)
        bundle?.let {
            intent.putExtra(packageName, bundle)
        }
        startActivity(intent)
        if (finish) {
            finish()
        }
    }


    fun getBundle(): Bundle? {
        return if (intent != null && intent.hasExtra(packageName)) {
            intent.getBundleExtra(packageName)
        }else {
            null
        }
    }

    fun connectMusicService() {
        MediaSessionConnection.getInstance().connect()
    }

    fun createDialog() {
        if (diaLog == null) {
            diaLog = LoadingDialog(this, "loading...")
        }
    }

    fun disconnectMusicService() {
        MediaSessionConnection.getInstance().disconnect()
    }

    fun showDialog() {
        diaLog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideDialog() {
        diaLog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }
}