package com.example.stage2_uikit

import android.content.Intent
import com.example.common.app.BaseActivity
import com.example.common.app.BaseFragment
import com.example.common.adapter_pager.FragmentAdapter
import com.example.factory.presenter.BasePresenter
import com.example.factory.presenter.IBase
import com.example.stage2_uikit.main.MainFragment
import com.example.stage2_uikit.second.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : BaseActivity(), IBase.View {

    //no MainPresenter yet
    override val presenter = BasePresenter(this)

    private lateinit var listFragment: MutableList<Pair<BaseFragment,String>>
    private lateinit var mainFragment: MainFragment
    private lateinit var secFragment: SecondFragment
    private lateinit var pagerAdapter: FragmentAdapter

    //find layout
    override val contentLayoutId = R.layout.activity_main

    //init widget
    override fun initWidget() {
        super.initWidget()
        initViewPagerAndTab()
        btn_collection.setOnClickListener {
            intent = Intent().setClass(this,CollectionActivity::class.java)
            startActivity(intent)
        }

    }

    //init viewPager,tab
    private fun initViewPagerAndTab(){
        mainFragment = MainFragment()
        secFragment = SecondFragment()
        main_tab.setupWithViewPager(main_viewpager)
        listFragment = mutableListOf(Pair(mainFragment,"一覧") ,Pair(secFragment,"応募情報"))
        pagerAdapter = FragmentAdapter(supportFragmentManager, listFragment)
        main_viewpager.adapter = pagerAdapter
    }





}
