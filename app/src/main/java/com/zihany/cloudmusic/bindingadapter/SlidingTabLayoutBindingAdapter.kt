package com.zihany.cloudmusic.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingTabLayout

class SlidingTabLayoutBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["setViewPager"], requireAll = false)
        fun setViewPager(layout: SlidingTabLayout, viewPager: ViewPager) {
            layout.setViewPager(viewPager)
        }
    }
}