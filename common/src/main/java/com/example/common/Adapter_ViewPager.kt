package com.example.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class Adapter_ViewPager(fm: FragmentManager?,val list: List<Pair<Fragment,String>>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {

        return list[position%list.size].first
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position%list.size].second
    }


}