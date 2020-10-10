package com.zihany.cloudmusic.util

import android.util.Log
import org.junit.Assert.*
import org.junit.Test

class TestClass {
    private var i = 1
    private var f = 0.5f
    private var s = "testClass"
    private var list = ArrayList<Int>(5)
}

class GsonUtilTest {
    var string: String = ""
    val tag = "GsonUtilTest"
    val test = TestClass()

    val expectedString = "{\"i\":1,\"f\":0.5,\"s\":\"testClass\",\"list\":[]}"
    @Test
    fun testToJson() {
        string = GsonUtil.toJson(test)
        assertEquals(expectedString, string)
    }

    @Test
    fun testFromJson() {
        val string = GsonUtil.toJson(test)
        val json = GsonUtil.fromJSON<TestClass>(string)
        assertEquals(test, json)
    }
}