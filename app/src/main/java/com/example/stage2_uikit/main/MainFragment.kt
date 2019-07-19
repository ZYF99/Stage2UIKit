package com.example.stage2_uikit.main

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.common.BaseFragment
import com.example.factory.data.Job
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(){




    override val layoutId: Int = R.layout.fragment_main
    lateinit var jobListAdapter: JobListAdapter
    val list = mutableListOf<Job>()

    //初始化数据
    override fun initBefore() {
        super.initBefore()
        val imgList_workstation = mutableListOf<String>(
            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1563500438&di=711486a47f23a623593cf5339204bd19&src=http://img8.byecity.com.cn/fs/brs/imgs/riyou/2015-02/tupian1.jpg"
        ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563510523317&di=64e444af826e8fbe931481de7adadd4f&imgtype=0&src=http%3A%2F%2Fwww.nieyun.com%2Fwp-content%2Fuploads%2F2011%2F07%2Fchaomengkeaixiaogoutupian1.jpg"
        ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563510523316&di=d58d1aa31de1e8f7ab30a3acaf57b42a&imgtype=0&src=http%3A%2F%2Fimg3.tupian1.com%2Fuploads%2Fallimg%2F170108%2F1-1F10R30432.jpg"
        )

        val list_photography = mutableListOf(
            Triple("http://pic37.nipic.com/20140113/8800276_184927469000_2.png","写真でPR","PRテキスト1のタイトル（任意項目）最大30文字アイウエオか"),
            Triple("http://pic37.nipic.com/20140113/8800276_184927469000_2.png","写真でPR","PRテキスト1のタイトル（任意項目）最大30文字アイウエオか"),
            Triple("http://pic37.nipic.com/20140113/8800276_184927469000_2.png","写真でPR","PRテキスト1のタイトル（任意項目）最大30文字アイウエオか"),
            Triple("http://pic37.nipic.com/20140113/8800276_184927469000_2.png","写真でPR","PRテキスト1のタイトル（任意項目）最大30文字アイウエオか")
        )



        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(false,imgList_workstation,list_photography))
        list.add(Job(false,imgList_workstation,list_photography))
        list.add(Job(false,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(false,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(false,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
        list.add(Job(true,imgList_workstation,list_photography))
    }


    //初始化控件
    override fun initWidget() {
        jobListAdapter = JobListAdapter(context!!, list)
        mainrec.layoutManager = LinearLayoutManager(context)
        mainrec.adapter = jobListAdapter

    }

}