package com.zihany.cloudmusic.bindingadapter

import android.graphics.Color
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["setupWithViewPager"], requireAll = false)
        fun setupWithViewPager(tabLayout: TabLayout, viewPager: ViewPager?) {
            tabLayout.setupWithViewPager(viewPager)
        }

        @JvmStatic
        @BindingAdapter(value = ["normalColor", "selectedColor"], requireAll = true)
        fun setTabTextColors(tabLayout: TabLayout, normalColor: String, selectedColor: String) {
            tabLayout.setTabTextColors(Color.parseColor(normalColor), Color.parseColor(selectedColor))
        }

    }
}