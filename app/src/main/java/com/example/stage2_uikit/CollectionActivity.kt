package com.example.stage2_uikit


import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.app.BaseActivity
import com.example.factory.data.Job
import com.example.factory.presenter.collection.CollectionPresenter
import com.example.factory.presenter.collection.ICollectionView
import com.example.stage2_uikit.main.JobListAdapter
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActivity(), ICollectionView, JobListAdapter.Listener {

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
        initList()
    }


    //listChanged CallBack
    private fun initList() {
        presenter.refreshList()
    }

    @SuppressLint("SetTextI18n")
    override fun onRefreshList(list: List<Job>) {
        jobListAdapter.replaceAll(list)
        tv_title_sec.text = "${list.size}件"
    }

    @SuppressLint("SetTextI18n")
    override fun onAddItem(job: Job) {
        jobListAdapter.addItem(job)

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
