package com.zihany.Cloudmusic

import android.app.Application
import com.lzx.starrysky.manager.MusicManager
import com.zihany.Cloudmusic.greendao.db.DaoMaster
import com.zihany.Cloudmusic.greendao.db.DaoSession
import org.greenrobot.greendao.database.Database

class App: Application() {
    companion object {
        private val TAG = "App"
        val DATA_BASE_NAME = "164MusicDao"
        private var context: App? = null
        private var daoSession: DaoSession? = null

        fun getContext(): App? {
            return context
        }

        fun getDaoSession(): DaoSession? {
            return daoSession
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        MusicManager.initMusicManager(this)
        initDataBase()
    }

    private fun initDataBase() {
        val helper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, DATA_BASE_NAME)
        val db: Database = helper.writableDb
        val daoMaster: DaoMaster = DaoMaster(db)
        daoSession = daoMaster.newSession()
    }
}