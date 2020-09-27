package com.zihany.cloudmusic.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.lzx.starrysky.manager.MediaSessionConnection
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.databinding.CommonTitleBinding
import com.zihany.cloudmusic.util.LocaleManageUtil
import com.zihany.cloudmusic.widget.LoadingDialog
import com.zihany.cloudmusic.widget.SearchEditText

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
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
        context = this
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
        } else {
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

    fun setBackBtn(color: String) {
        val backBtn = findViewById<ImageView>(R.id.iv_back)
        backBtn.visibility = View.VISIBLE
        val vectorDrawableCompat = VectorDrawableCompat.create(resources, R.drawable.shape_back, theme)
        vectorDrawableCompat?.setTint(Color.parseColor(color))
        backBtn.setImageDrawable(vectorDrawableCompat)
        backBtn.setOnClickListener {
            System.gc()
            onBackPressed()
        }
    }

    fun setLeftTitleText(text: String) {
        val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title)
        tvLeftTitle.visibility = View.VISIBLE
        tvLeftTitle.text = text
    }

    fun setLeftTitleText(titleText: String, textColor: String) {
        val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title)
        tvLeftTitle.visibility = View.VISIBLE
        tvLeftTitle.text = titleText
        tvLeftTitle.setTextColor(Color.parseColor(textColor))
    }

    fun setLeftTitleTextGone() {
        val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title)
        tvLeftTitle.visibility = View.GONE
    }

    fun setLeftTitleTextColorWhite() {
        val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title)
        tvLeftTitle.setTextColor(Color.parseColor("#ffffff"))
    }

    fun setLeftTitleAlpha(alpha: Float) {
        val tvLeftTitle = findViewById<TextView>(R.id.tv_left_title)
        tvLeftTitle.visibility = View.VISIBLE
        tvLeftTitle.alpha = alpha
    }

    fun setRightSearchButton() {
        val btnSearch = findViewById<TextView>(R.id.btn_search)
        btnSearch.visibility = View.VISIBLE
    }

    fun setEditText(textColor: String) {
        val etSearch = findViewById<SearchEditText>(R.id.et_search)
        etSearch.visibility = View.VISIBLE
        etSearch.setEditTextColor(textColor)
    }
}