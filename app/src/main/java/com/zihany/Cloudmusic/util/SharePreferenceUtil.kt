package com.zihany.Cloudmusic.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract
import com.google.gson.reflect.TypeToken
import com.lzx.starrysky.model.SongInfo
import com.zihany.Cloudmusic.base.Constants
import com.zihany.Cloudmusic.login.bean.LoginBean
import java.lang.reflect.Type
import java.util.*

class SharePreferenceUtil private constructor() {
    companion object {
        private val TAG = "SharePreferenceUtil"
        private var sp: SharedPreferences? = null
        private lateinit var editor: SharedPreferences.Editor
        private var instance: SharePreferenceUtil? = null

        fun getInstance(context: Context): SharePreferenceUtil {
            if (instance == null) {
                synchronized(SharePreferenceUtil::class.java) {
                    if (instance == null) {
                        init(context)
                        instance = SharePreferenceUtil()
                    }
                }
            }
            return instance!!
        }

        @SuppressLint("CommitPrefEdits")
        private fun init(context: Context) {
            if (sp == null) {
                sp = context.getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
            }
            editor = sp!!.edit()
        }
    }
    private val systemCurrentLocal: Locale = Locale.CHINESE

    fun getSelectLanguage(): Int {
        return getInt(Constants.TAG_LANGUAGE, 0)
    }

    fun getUserInfo(defaultValue: String): String {
        return getString(Constants.USER_INFO, defaultValue)?:""
    }

    fun saveUserInfo(bean: LoginBean, phoneNumber: String) {
        if (bean.binding!!.size > 1) {
            saveAuthToken(bean.binding!![1].tokenJsonStr!!)
        }
        saveAccountNum(phoneNumber)
        saveString(Constants.USER_INFO, GsonUtil.toJson(bean))
    }

    private fun saveAccountNum(phoneNumber: String) {
        saveString(Constants.USER_INFO, phoneNumber)
    }

    private fun saveAuthToken(token: String) {
        saveString(Constants.AUTH_TOKEN, token)
    }

    fun getAuthToken(defaultValue: String): String? {
        return getString(Constants.AUTH_TOKEN, defaultValue)
    }

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun saveLatestSong(songInfo: SongInfo) {
        val song = GsonUtil.toJson(songInfo)
        saveString(Constants.LATEST_SONG, song)
    }

    fun getLateSong(): SongInfo? {
        return getString(Constants.LATEST_SONG, "")?.let {
            GsonUtil.fromJSON<SongInfo>(it)
        }
    }

    fun saveDailyUpdateTime(updateTime: Long) {
        saveLong(Constants.DAILY_UPDATE_TIME, updateTime)
    }

    fun getDailyUpdateTime(): Long {
        return getLong(Constants.DAILY_UPDATE_TIME, 0)
    }

    fun saveLikeList(likeList: List<String>) {
        val likeListString = GsonUtil.toJson(likeList)
        saveString(Constants.LIKE_LIST, likeListString)
    }

    fun getLikeList(): List<String>? {
        val likeListString = getString(Constants.LIKE_LIST, "")
        val itemType = object : TypeToken<List<String>>() {}.type
        return likeListString?.let { GsonUtil.fromJSON<List<String>>(it) }
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return sp!!.getLong(key, defaultValue)
    }

    private fun saveLong(key: String, values: Long) {
        editor.putLong(key, values)
    }

    private fun getString(key: String, defaultValue: String): String? {
        return sp!!.getString(key, defaultValue)
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return sp!!.getInt(key, defaultValue)
    }

    fun getSystemCurrentLocal(): Locale {
        return systemCurrentLocal
    }


}