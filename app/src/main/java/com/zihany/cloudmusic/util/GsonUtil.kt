package com.zihany.cloudmusic.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class GsonUtil {
    companion object {
        const val TAG = "GsonUtil"
        val instance: Gson by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            GsonBuilder().serializeNulls().create()
        }

        fun toJson(any: Any): String {
            //LogUtil.d(TAG, "toJson:${result}")
            return instance.toJson(any)
        }

        fun <T> fromJSON(json: String?): T? {
            if (json == null) {
                return null
            }
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