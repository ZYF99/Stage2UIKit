package com.example.stage2_uikit.main


import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.app.BaseFragment
import com.example.factory.data.Job
import com.example.factory.presenter.BasePresenter
import com.example.factory.presenter.main.IMainView
import com.example.factory.presenter.main.MainPresenter
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.fragment_main.*
class MainFragment : BaseFragment(), IMainView, JobListAdapter.Listener {

    override val presenter = MainPresenter(this)


    override val layoutId: Int = R.layout.fragment_main
    private lateinit var jobListAdapter: JobListAdapter
    private val list = mutableListOf<Job>()


    //widget init
    override fun initWidget(view: View) {
        jobListAdapter = JobListAdapter(context!!, list, this)
        mainrec.layoutManager = LinearLayoutManager(context)
        mainrec.adapter = jobListAdapter
        refreshlayout.setColorSchemeResources(R.color.colorAccent)
        refreshlayout.setOnRefreshListener {
            presenter.getJobList(0)
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
        Handler().postDelayed({
            presenter.getJobList(0)
        }, 2000)
    }

    //add jobList
    override fun addJobList(list: List<Job>) {
        jobListAdapter.addAll(list)
        if (refreshlayout.isRefreshing)
            refreshlayout.isRefreshing = false
    }

    //refresh jobList
    override fun refreshJobList(list: List<Job>) {
        jobListAdapter.replaceAll(list)
        refreshlayout.isRefreshing = false
    }

    //keepbtn click event
    override fun onCollectBtnClick(pos: Int) {
        presenter.collectJob(list[pos])

    }

    //collect callBack
    override fun onTriggerCollect(job: Job) {
        jobListAdapter.triggerCollectItem(job)
    }


}