package com.zihany.cloudmusic.base

import com.zihany.cloudmusic.api.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> ApiResponse<T>): ApiResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: ApiResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {

        return coroutineScope {
//            if (response.errorCode == -1) {
//                errorBlock?.let {
//                    it()
//                }
//                Result.Error(IOException(response.errorMsg))
//            } else {
                successBlock?.let {
                    it()
                }
                Result.Success(response.data)
//            }
        }
    }
}