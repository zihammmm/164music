package com.zihany.cloudmusic.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.base.PHONE_NUMBER
import com.zihany.cloudmusic.base.SHARED_PREFERENCE_FILE_NAME
import com.zihany.cloudmusic.login.bean.LoginBean
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 使用委托属性封装
 */
class PreferenceUtils<T>(val name: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        const val IS_LOGIN = "is_login"
        const val USER_GSON = "user_gson"
        const val TAG = "Preference"
    }

    private val prefs: SharedPreferences by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        App.getContext().getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(name, default)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getValue(name: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> Any()
        }
        return res as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(name, value)
    }

    private fun <T> putValue(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    @Suppress("UNCHECKED_CAST")
    private fun <A> deSerialization(str: String): A {
        val redStr = URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream
        )
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
}
