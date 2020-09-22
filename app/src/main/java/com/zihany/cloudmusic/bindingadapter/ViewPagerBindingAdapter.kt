package com.zihany.cloudmusic.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter

class ViewPagerBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["setAdapter"], requireAll = false)
        fun setAdapter(viewPager: ViewPager, pagerAdapter: MultiFragmentPagerAdapter?) {
            pagerAdapter?.let {
                viewPager.adapter = it
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["setOffscreenPageLimit"], requireAll = false)
        fun setOffscreenPageLimit(viewPager: ViewPager, limit: Int) {
            viewPager.offscreenPageLimit = limit
        }

        @JvmStatic
        @BindingAdapter(value = ["setCurrentItem"], requireAll = false)
        fun setCurrentItem(viewPager: ViewPager, item: Int) {
            viewPager.currentItem = item
        }
    }
}