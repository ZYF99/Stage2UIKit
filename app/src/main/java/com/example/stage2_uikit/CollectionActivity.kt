package com.example.stage2_uikit


import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.app.BaseActivity
import com.example.factory.data.Job
import com.example.factory.presenter.bindLife
import com.example.factory.presenter.collection.CollectionPresenter
import com.example.factory.presenter.collection.ICollection
import com.example.factory.presenter.runRxSingle
import com.example.stage2_uikit.main.JobListAdapter
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActivity(), ICollection.View, JobListAdapter.Listener {

    override val presenter = CollectionPresenter(this)

    override val contentLayoutId = R.layout.activity_collection

    private lateinit var jobListAdapter: JobListAdapter


    override fun initWidget() {
        super.initWidget()
        jobListAdapter = JobListAdapter(this, presenter.jobList, this)
        rec_collection.layoutManager = LinearLayoutManager(this)
        rec_collection.adapter = jobListAdapter
        btn_back.setOnClickListener {
            finish()
        }
        refreshList()
    }


    //listChanged CallBack
    @SuppressLint("SetTextI18n")
    private fun refreshList() {

        runRxSingle(true, presenter.getJobList())
            .doOnSuccess { newList ->
                newList.map { item ->
                    item.isCollected = true
                }
                jobListAdapter.replaceAll(newList)
                tv_title_sec.text = "${newList.size}件"
            }.bindLife(presenter.compositeDisposable)

    }

    @SuppressLint("SetTextI18n")
    override fun onRemove(job: Job) {
        jobListAdapter.removeItem(job)
        tv_title_sec.text = "${presenter.jobList.size}件"
    }

    override fun onCollectBtnClick(pos: Int) {
        presenter.deleteItem(pos)
    }
}
