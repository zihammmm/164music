package com.zihany.cloudmusic.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.zihany.cloudmusic.App
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiEngine {
    companion object {
        val instance: ApiEngine by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiEngine()
        }
    }
    private var retrofit: Retrofit

    init {
        val netWorkInterceptor = NetWorkInterceptor()
        val responseInterceptor = ResponseInterceptor()

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.getContext()))
        val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(netWorkInterceptor)
                .addInterceptor(responseInterceptor)
                .cookieJar(cookieJar)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}