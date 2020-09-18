package com.zihany.lib_toast.style

import android.view.Gravity

class BlackStyle: IBaseStyle {
    override var gravity: Int
        get() = Gravity.CENTER
        set(value) {}
    override var xOffset: Int
        get() = 0
        set(value) {}
    override var yOffset: Int
        get() = 0
        set(value) {}
    override var z: Int
        get() = 30
        set(value) {}
    override var cornerRadius: Int
        get() = 6
        set(value) {}
    override var backgroundColor: Long
        get() = 0x880000000
        set(value) {}
    override var textColor: Long
        get() = 0xeeffffff
        set(value) {}
    override var textSize: Int
        get() = 14
        set(value) {}
    override var maxLines: Int
        get() = 3
        set(value) {}
    override var paddingLeft: Int
        get() = 24
        set(value) {}
    override var paddingTop: Int
        get() = 16
        set(value) {}
    override var paddingRight: Int
        get() = paddingLeft
        set(value) {}
    override var paddingBottom: Int
        get() = paddingTop
        set(value) {}
}