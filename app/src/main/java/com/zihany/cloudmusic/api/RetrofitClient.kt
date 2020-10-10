package com.zihany.cloudmusic.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zihany.cloudmusic.App
import okhttp3.OkHttpClient

class RetrofitClient: BaseRetrofitClient() {
    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }
    private val cookieJar by lazy { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext())) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        TODO("Not yet implemented")
    }
}