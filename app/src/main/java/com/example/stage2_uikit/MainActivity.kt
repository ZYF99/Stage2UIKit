package com.example.stage2_uikit

import androidx.fragment.app.Fragment
import com.example.common.Activity
import com.example.common.Adapter_ViewPager
import com.example.stage2_uikit.main.MainFragment
import com.example.stage2_uikit.second.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {


    private lateinit var list_fragment: List<Pair<Fragment,String>>
    private lateinit var mainFragment: MainFragment
    private lateinit var secFragment: SecondFragment
    private lateinit var pagerAdapter: Adapter_ViewPager

    //找到布局
    override val contentLayoutId = R.layout.activity_main


    //初始化控件
    override fun initWidget() {
        super.initWidget()
        mainFragment = MainFragment()
        secFragment = SecondFragment()
        main_tab.setupWithViewPager(main_viewpager)
        list_fragment = listOf(Pair(mainFragment,"一覧") ,Pair(secFragment,"応募情報"))
        pagerAdapter = Adapter_ViewPager(supportFragmentManager,list_fragment)
        main_viewpager.adapter = pagerAdapter
    }


}
