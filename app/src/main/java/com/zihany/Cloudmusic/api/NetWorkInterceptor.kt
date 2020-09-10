package com.zihany.Cloudmusic.api

import com.zihany.Cloudmusic.util.NetUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class NetWorkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!NetUtil.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val response = chain.proceed(request)

        return if (NetUtil.isConnected()) {
            val maxStale = 0
            response.newBuilder()
                    .header("Cache-Control", "public,max-age=${maxStale}")
                    .build()
        }else {
            val maxStale = 60 * 60 * 24
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cache, max-stale=${maxStale}")
                    .build()
        }

    }

}