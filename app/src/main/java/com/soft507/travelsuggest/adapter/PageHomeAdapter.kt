package com.soft507.travelsuggest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.soft507.travelsuggest.fragment.TravelSuggestFragment

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/1 17:06
 */
class PageHomeAdapter(private val fragmentList: ArrayList<Fragment>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return TravelSuggestFragment()
    }

    override fun getCount(): Int = fragmentList.size

}