package com.zihany.Cloudmusic.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type

class GsonUtil {
    companion object {
        val TAG = "GsonUtil"
        val instance: Gson by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            GsonBuilder().serializeNulls().create()
        }

        fun toJson(any: Any): String {
            val result = instance.toJson(any)
            LogUtil.d(TAG, "toJson:${result}")
            return result
        }

        fun <T> fromJSON(json: String): T? {
            try {
                val itemType = object : TypeToken<T>(){}.type
                return instance.fromJson<T>(json, itemType)
            }catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}