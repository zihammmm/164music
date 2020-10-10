package com.zihany.cloudmusic.api

import android.util.Log
import com.zihany.cloudmusic.login.mvvm.model.LoginRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class ApiEngineTest {
    companion object {
        const val TAG = "ApiEngineTest"
        const val TEST_PHONE = "13112517479"
        const val TEST_PASSWORD = "Yzh19971123."
    }


    @Before
    fun setUp() {
        //Log.d(TAG, "test begin")
    }

    @Test
    fun testLogin() = runBlocking {
        val apiService = ApiEngine.instance.getApiService()
        val result = apiService.login(TEST_PHONE, TEST_PASSWORD)
        assertNotNull(result)
    }

    @After
    fun tearDown() {
    }
}