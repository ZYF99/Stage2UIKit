package com.example.factory.presenter.main


import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.presenter.BasePresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainPresenter(val view: IMain) : BasePresenter(), IMain.Presenter {

    val list = mutableListOf<Job>()

    override fun intervalTest() {
        Observable.interval(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Long> {

            lateinit var disposable: Disposable

            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: Long) {

            }

            override fun onError(e: Throwable) {

            }
        })
    }

    override fun getJobListRx(offset: Int): Observable<List<Job>> {
        return NetWorkManager.getRequest().getJobList(offset)
    }

    @SuppressLint("CheckResult")
    override fun collectJobRx(job: Job):Observable<Job>{
        return Observable.create<Job> { e -> e.onNext(job)  }
    }

}