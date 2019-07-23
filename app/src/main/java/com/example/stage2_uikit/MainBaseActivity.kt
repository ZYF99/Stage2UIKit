package com.example.stage2_uikit

import com.example.common.BaseActivity
import com.example.common.BaseFragment
import com.example.common.adapter_pager.FragmentAdapter
import com.example.stage2_uikit.main.MainFragment
import com.example.stage2_uikit.second.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*



class MainBaseActivity : BaseActivity() {


    private lateinit var listFragment: MutableList<Pair<BaseFragment,String>>
    private lateinit var mainFragment: MainFragment
    private lateinit var secFragment: SecondFragment
    private lateinit var pagerAdapter: FragmentAdapter

    //找到布局
    override val contentLayoutId = R.layout.activity_main


    //初始化控件
    override fun initWidget() {
        super.initWidget()
        mainFragment = MainFragment()
        secFragment = SecondFragment()
        main_tab.setupWithViewPager(main_viewpager)
        listFragment = mutableListOf(Pair(mainFragment,"一覧") ,Pair(secFragment,"応募情報"))
        pagerAdapter = FragmentAdapter(supportFragmentManager, listFragment)
        main_viewpager.adapter = pagerAdapter
    }


}
