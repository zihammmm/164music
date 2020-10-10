package com.zihany.cloudmusic.api

import com.zihany.cloudmusic.base.Result
import com.zihany.cloudmusic.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

data class ApiResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)
const val TAG = "ApiResponse"

suspend fun <T : Any> ApiResponse<T>.executeResponse(
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {

    return coroutineScope {
//        if (errorCode == -1) {
//            errorBlock?.let {
//                it()
//            }
//            Result.Error(IOException(errorMsg))
//        } else {
            successBlock?.let { it() }
            Result.Success(data)
//        }
    }
}

suspend fun <T : Any> ApiResponse<T>.doSuccess(
        successBlock: (suspend CoroutineScope.(T) -> Unit)? = null): ApiResponse<T> {
//    LogUtil.d(TAG, "doSuccess error: $errorCode")
    return coroutineScope {
//        if (errorCode != -1) {
            successBlock?.invoke(this, this@doSuccess.data)
//        }
        this@doSuccess
    }
}

suspend fun <T:Any> ApiResponse<T>.doError(
        errorBlock: (suspend CoroutineScope.(String) -> Unit)? = null
): ApiResponse<T> {
//    LogUtil.d(TAG, "doError error: $errorCode")
    return coroutineScope {
//        if (errorCode == -1) {
//            errorBlock?.invoke(this, this@doError.errorMsg)
//        }
        this@doError
    }
}