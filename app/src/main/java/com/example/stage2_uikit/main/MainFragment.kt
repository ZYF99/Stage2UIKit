package com.example.stage2_uikit.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.factory.data.Bean_mainList
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : com.example.common.Fragment() {

    override val layoutId: Int = R.layout.fragment_main
    lateinit var adapter_mainList: Adapter_mainList
    val list = mutableListOf<Bean_mainList>()

    //初始化数据
    override fun initBefore() {
        super.initBefore()
        list.add(Bean_mainList(true))
        list.add(Bean_mainList(true))
        list.add(Bean_mainList(false))
        list.add(Bean_mainList(false))
        list.add(Bean_mainList(false))
        list.add(Bean_mainList(true))
        list.add(Bean_mainList(true))
    }


    //初始化控件
    override fun initWidget() {

        adapter_mainList = Adapter_mainList(context!!, list)
        mainrec.layoutManager = LinearLayoutManager(context)
        mainrec.adapter = adapter_mainList
    }

}