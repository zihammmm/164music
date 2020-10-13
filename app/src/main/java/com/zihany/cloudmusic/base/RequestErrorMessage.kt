package com.zihany.cloudmusic.base

data class RequestErrorMessage(
    val body: Body,
    val status: Int
)

data class Body(
    val code: Int,
    val msg: Any
)