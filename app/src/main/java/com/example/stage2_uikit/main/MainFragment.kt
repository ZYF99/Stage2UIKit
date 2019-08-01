package com.example.stage2_uikit.main


import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.app.BaseFragment
import com.example.factory.presenter.bindLife
import com.example.factory.presenter.main.IMain
import com.example.factory.presenter.main.MainPresenter
import com.example.factory.presenter.runRxSingle
import com.example.factory.utils.SharedPreferencesUtil
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.fragment_main.*
import android.annotation.SuppressLint as SuppressLint1

class MainFragment : BaseFragment(), IMain.View, JobListAdapter.Listener {

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

        runRxSingle(true, presenter.getJobListRx(offset)).doOnSuccess { list ->
            list.map { job ->
                if (SharedPreferencesUtil.getListData("collections", String::class.java).contains(job.id))
                    job.isCollected = true
            }
            //refresh list
            jobListAdapter.replaceAll(list)
            refreshlayout.isRefreshing = false
        }.bindLife(presenter.compositeDisposable)


    }


    //keepbtn click event
    override fun onCollectBtnClick(pos: Int) {

        runRxSingle(false, presenter.collectJobRx(presenter.list[pos]).doOnSuccess { job ->
            val list = SharedPreferencesUtil.getListData("collections", String::class.java)
            if (job.isCollected) {
                list.remove(job.id)
            } else {
                list.add(job.id)
            }
            SharedPreferencesUtil.putListData("collections", list)
            //Trigger Collect
            jobListAdapter.triggerCollectItem(job)
        }).bindLife(presenter.compositeDisposable)

    }
}