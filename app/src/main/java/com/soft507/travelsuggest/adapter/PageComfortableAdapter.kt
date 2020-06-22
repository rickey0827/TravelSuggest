package com.soft507.travelsuggest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.soft507.travelsuggest.fragment.VideoAreaFragment

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/9 0:59
 */
class PageComfortableAdapter(
    private val fragmentList: ArrayList<Fragment>,
    private val titles: Array<String?>, fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return VideoAreaFragment()
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = fragmentList.size
}