package com.zihany.Cloudmusic.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lzx.starrysky.model.SongInfo
import com.zihany.Cloudmusic.widget.LoadingDialog

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener{
    companion object {
        val SONG_URL = "http://music.163.com/song/media/outer/url?id="
    }

    val TAG = "BaseActivity"
    var context: Context? = null

    protected var diaLog: LoadingDialog? = null
    private var bottomSongInfo: SongInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context  = this

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    fun createDialog() {
        if (diaLog == null) {
            diaLog = LoadingDialog(this, "loading...")
        }
    }
}