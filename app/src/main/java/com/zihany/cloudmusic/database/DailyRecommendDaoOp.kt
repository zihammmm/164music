package com.zihany.cloudmusic.database

import com.zihany.cloudmusic.main.bean.DRGreenDaoBean

class DailyRecommendDaoOp {
    companion object {
        fun insertData(listBean: List<DRGreenDaoBean>) {
            DbManager.daoSession.drGreenDaoBeanDao.insertInTx(listBean)
        }

        fun saveData(listBean: List<DRGreenDaoBean>) {
            DbManager.daoSession.drGreenDaoBeanDao.saveInTx(listBean)
        }

        fun deleteAllData() {
            DbManager.daoSession.drGreenDaoBeanDao.deleteAll()
        }

        fun queryAll(): List<DRGreenDaoBean> {
            val builder = DbManager.daoSession.drGreenDaoBeanDao.queryBuilder()
            return builder.build().list()
        }
    }
}