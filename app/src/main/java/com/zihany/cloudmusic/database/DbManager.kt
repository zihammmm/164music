package com.zihany.cloudmusic.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.greendao.db.DaoMaster
import com.zihany.cloudmusic.greendao.db.DaoSession

object DbManager {
    const val ENCRYPTED = true
    lateinit var daoMaster: DaoMaster
    lateinit var devOpenHelper: DaoMaster.DevOpenHelper
    lateinit var daoSession: DaoSession
    lateinit var context: Context

    fun init(newContext: Context) {
        context = newContext
        devOpenHelper = DaoMaster.DevOpenHelper(context, App.DATA_BASE_NAME)
        daoMaster = DaoMaster(devOpenHelper.writableDatabase!!)
        daoSession = daoMaster!!.newSession()
    }
}