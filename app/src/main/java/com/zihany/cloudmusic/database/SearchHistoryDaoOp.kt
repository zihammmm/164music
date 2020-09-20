package com.zihany.cloudmusic.database

import android.content.Context
import com.zihany.cloudmusic.search.bean.SearchHistoryBean

class SearchHistoryDaoOp {
    companion object {
        const val TAG = "SearchHistoryDaoOp"

        fun insertData(listBean: ArrayList<SearchHistoryBean>) {
            if (listBean.isNotEmpty()) {
                DbManager.daoSession.searchHistoryBeanDao.insertInTx(listBean)
            }
        }

        fun saveData(listBean: ArrayList<SearchHistoryBean>) {
            deleteAllData()
            DbManager.daoSession.searchHistoryBeanDao.saveInTx(listBean)
        }

        fun deleteAllData() {
            DbManager.daoSession.searchHistoryBeanDao.deleteAll()
        }

        fun queryAll(): List<SearchHistoryBean>? {
            val builder = DbManager.daoSession.searchHistoryBeanDao.queryBuilder()
            return builder.build().list()
        }
    }
}