package com.zihany.cloudmusic.song.bean

data class LyricBean(
    val code: Int,
    val klyric: Klyric,
    val lrc: Lrc?,
    val lyricUser: LyricUser,
    val qfy: Boolean,
    val sfy: Boolean,
    val sgc: Boolean,
    val tlyric: Tlyric,
    val transUser: TransUser
)

data class Klyric(
    val lyric: Any,
    val version: Int
)

data class Lrc(
    val lyric: String,
    val version: Int
)

data class LyricUser(
    val demand: Int,
    val id: Int,
    val nickname: String,
    val status: Int,
    val uptime: Long,
    val userid: Int
)

data class Tlyric(
    val lyric: String?,
    val version: Int
)

data class TransUser(
    val demand: Int,
    val id: Int,
    val nickname: String,
    val status: Int,
    val uptime: Long,
    val userid: Int
)