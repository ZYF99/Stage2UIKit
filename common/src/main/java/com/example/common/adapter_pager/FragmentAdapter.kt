package com.example.common.adapter_pager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.common.BaseFragment

class FragmentAdapter(fm: FragmentManager?, val list: MutableList<Pair<BaseFragment,String>>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = list[position].first

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].second
    }



}