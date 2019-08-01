package com.example.factory.presenter.main


import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.presenter.BasePresenter
import io.reactivex.Single



class MainPresenter(mView: IMain.View) : BasePresenter(mView), IMain.Presenter {

    val list = mutableListOf<Job>()


    override fun getJobListRx(offset: Int): Single<List<Job>> {
        return NetWorkManager.getRequest().getJobList(offset)
    }

    @SuppressLint("CheckResult")
    override fun collectJobRx(job: Job):Single<Job>{
        return Single.create<Job> { e -> e.onSuccess(job) }
    }

}