package com.example.stage2_uikit.main

import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.app.BaseFragment
import com.example.factory.data.Job
import com.example.factory.net.runDisRx
import com.example.factory.presenter.main.IMain
import com.example.factory.presenter.main.MainPresenter
import com.example.factory.utils.SharedPreferencesUtil
import com.example.stage2_uikit.R
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), IMain, JobListAdapter.Listener {

    override val presenter = MainPresenter(this)
    override val layoutId: Int = R.layout.fragment_main
    private lateinit var jobListAdapter: JobListAdapter

    //widget init
    override fun initWidget(view: View) {
        jobListAdapter = JobListAdapter(context!!, presenter.list, this)
        mainrec.layoutManager = LinearLayoutManager(context)
        mainrec.adapter = jobListAdapter
        refreshlayout.setColorSchemeResources(R.color.colorAccent)

        refreshlayout.setOnRefreshListener {
            //presenter.getJobList(0)
            getJobList(0)

        }
    }

    //data init
    override fun initData() {
        super.initData()
        initList()
    }

    //the first init data before initWidget
    private fun initList() {
        refreshlayout.isRefreshing = true
        //presenter.getJobList(0)
        Handler().postDelayed({
            getJobList(0)
        }, 2000)
    }

    //get job list from net
    private fun getJobList(offset: Int) {
        runDisRx(presenter.getJobListRx(offset), object : DisposableObserver<List<Job>>() {
            override fun onComplete() {}

            override fun onNext(newList: List<Job>) {
                newList.map {
                    if (SharedPreferencesUtil.getListData("collections", String::class.java).contains(it.id))
                        it.isCollected = true
                }
                //refresh list
                jobListAdapter.replaceAll(newList)
                refreshlayout.isRefreshing = false
            }

            override fun onError(e: Throwable) {
                println("~~~Error~~~" + e.message)
            }
        })
    }


    //keepbtn click event
    override fun onCollectBtnClick(pos: Int) {
        runDisRx(presenter.collectJobRx(presenter.list[pos]), object : DisposableObserver<Job>() {
            override fun onComplete() {}
            override fun onNext(job: Job) {
                val list = SharedPreferencesUtil.getListData("collections", String::class.java)
                if (job.isCollected) {
                    list.remove(job.id)
                } else {
                    list.add(job.id)
                }
                SharedPreferencesUtil.putListData("collections", list)
                //Trigger Collect
                jobListAdapter.triggerCollectItem(job)
            }

            override fun onError(e: Throwable) {}
        })
    }


}