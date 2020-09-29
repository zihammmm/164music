package com.zihany.cloudmusic.api

class ApiResponse<T>(nothing: Nothing?, i: Int, s: String) {
    var data: T? = nothing
    var errorCode: Int = i
    var errorMsg: String = s

}