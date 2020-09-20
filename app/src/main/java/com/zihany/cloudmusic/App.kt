package com.zihany.cloudmusic

import android.app.Application
import com.lzx.starrysky.manager.MusicManager
import com.zihany.cloudmusic.database.DbManager
import com.zihany.cloudmusic.greendao.db.DaoMaster
import com.zihany.cloudmusic.greendao.db.DaoSession
import com.zihany.cloudmusic.util.ToastUtils
import org.greenrobot.greendao.database.Database

class App: Application() {
    companion object {
        const val TAG = "App"
        val DATA_BASE_NAME = "164MusicDao"
        private lateinit var context: App
        private var daoSession: DaoSession? = null

        fun getContext(): App {
            return context
        }

        fun getDaoSession(): DaoSession? {
            return daoSession
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        ToastUtils.init(this)
        MusicManager.initMusicManager(this)
        DbManager.init(this)
        initDataBase()
    }

    private fun initDataBase() {
        val helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, DATA_BASE_NAME)
        val db: Database = helper.writableDb
        val daoMaster = DaoMaster(db)
        daoSession = daoMaster.newSession()
    }
}