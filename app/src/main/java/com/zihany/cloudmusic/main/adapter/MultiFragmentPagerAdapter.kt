package com.zihany.cloudmusic.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.zihany.cloudmusic.base.BaseFragment

class MultiFragmentPagerAdapter constructor(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val TAG = "MultiFragmentPagerAdapter"
    }

    private val fragments: MutableList<BaseFragment> = ArrayList()

    fun init(fragmentList: MutableList<BaseFragment>) {
        fragments.clear()
        fragments.addAll(fragmentList)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int = fragments.size

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val something = getItem(position)
        return if (something is BaseFragment) {
            something.fragmentTitle
        }else {
            super.getPageTitle(position)
        }
    }
}