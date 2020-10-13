package com.zihany.cloudmusic.api

import com.zihany.cloudmusic.util.LogUtil
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.StandardCharsets

class ResponseInterceptor: Interceptor{
    companion object{
        const val TAG = "ResponseInterceptor"
        val UTF8 = StandardCharsets.UTF_8
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body
        if (response.code != 200) {
            return response
        }
        val contentLength = responseBody!!.contentLength()

        if (!bodyEncoded(response.headers)) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer

            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }

            if (contentLength != 0L) {
                val result = buffer.clone().readString(charset)
                LogUtil.d(TAG, "response.url():${response.request.url}")
                LogUtil.d(TAG, "response.body():${result}")
                println("response.body():${result}")
            }
        }
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", true)
    }
}