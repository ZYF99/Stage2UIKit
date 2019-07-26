package com.example.factory.presenter.main

import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.net.runDisRx
import com.example.factory.presenter.BasePresenter
import com.example.factory.utils.SharedPreferencesUtil
import io.reactivex.observers.DisposableObserver


class MainPresenter(val view: IMainView) : BasePresenter(), IMainPresenter {

    override fun getJobList(offset: Int) {
        val request = NetWorkManager.getRequest()
        val disposable =
            runDisRx(request.getJobList(offset), object : DisposableObserver<List<Job>>() {
                override fun onComplete() {
                }

                override fun onNext(t: List<Job>) {
                    print(t)
                    t.map {
                        if (SharedPreferencesUtil.getListData("collections", String::class.java).contains(it.id))
                            it.isCollected = true
                    }
                    if (offset == 0) view.refreshJobList(t)
                    else view.addJobList(t)
                    println("~~~~~~$t")
                }

                override fun onError(e: Throwable) {
                    println("~~~Error~~~" + e.message)
                }
            })
        disposables.add(disposable)
    }

    override fun collectJob(job: Job) {
        val list = SharedPreferencesUtil.getListData("collections", String::class.java)
        if (job.isCollected) {
            list.remove(job.id)
        } else {
            list.add(job.id)
        }
        SharedPreferencesUtil.putListData("collections", list)
        view.onTriggerCollect(job)
    }


}