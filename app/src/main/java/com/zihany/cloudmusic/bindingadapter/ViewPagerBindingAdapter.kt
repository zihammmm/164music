package com.zihany.cloudmusic.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.util.LogUtil

class ViewPagerBindingAdapter {
    companion object {
        const val TAG = "ViewPagerBindingAdapter"

        @JvmStatic
        @BindingAdapter(value = ["android:adapter"], requireAll = false)
        fun setAdapter(viewPager: ViewPager, pagerAdapter: MultiFragmentPagerAdapter?) {
            LogUtil.d(TAG, "setAdapter")
            pagerAdapter?.let {
                viewPager.adapter = it
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["android:offscreenPageLimit"], requireAll = false)
        fun setOffscreenPageLimit(viewPager: ViewPager, limit: Int) {
            LogUtil.d(TAG, "setOffscreenPageLimit")
            viewPager.offscreenPageLimit = limit
        }

        @JvmStatic
        @BindingAdapter(value = ["android:currentItem"], requireAll = false)
        fun setCurrentItem(viewPager: ViewPager, item: Int) {
            LogUtil.d(TAG, "setCurrentItem")
            viewPager.currentItem = item
        }
    }
}