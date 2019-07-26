package com.example.factory.presenter.collection

import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.net.runRxFromList
import com.example.factory.presenter.BasePresenter
import com.example.factory.utils.SharedPreferencesUtil
import io.reactivex.*
import io.reactivex.disposables.Disposable



class CollectionPresenter(val view: ICollectionView) : BasePresenter(), ICollection {

    val jobList = mutableListOf<Job>()
    private var idList = mutableListOf<String>()

    @SuppressLint("CheckResult")
    override fun refreshList() {
        val request = NetWorkManager.getRequest()
        idList = SharedPreferencesUtil.getListData("collections", String::class.java)

        //get whole List by Rxjava（toList）
        runRxFromList({ request.getJob(it) }, idList, object : SingleObserver<List<Job>> {
            override fun onSuccess(t: List<Job>) {
                t.map {
                    it.isCollected = true
                }
                view.onRefreshList(t)
            }

            override fun onSubscribe(d: Disposable) {}
            override fun onError(e: Throwable) {}
        })


    }

    override fun deleteItem(pos: Int) {
        idList.removeAt(pos)
        SharedPreferencesUtil.putListData("collections", idList)
        view.onRemove(jobList[pos])
    }
}


/*

//用toList()整合getJob单个结果为list返回************************************************************************************
 //未使用包装方法的

 Observable.fromIterable(idList)
            .flatMap { id ->
                request.getJob(id)
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Job>> {
                override fun onSuccess(t: List<Job>) {
                    t.map {
                        it.isCollected = true
                    }
                    view.onRefreshList(t)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })*/

//map 调用getJob 只有异步的单个返回************************************************************************************
/*
    //使用了包装方法runDisRx
 idList.map {
            disposables = CompositeDisposable()
            val disposable = runDisRx(request.getJob(it), object : DisposableObserver<Job>() {
                override fun onComplete() {
                }

                override fun onNext(t: Job) {
                    t.isCollected = true
                    t.let { it1 -> view.onAddItem(it1) }
                    println("${jobList.size}job~SIZE~id${idList.size}")
                }

                override fun onError(e: Throwable) {
                }
            }
            )
            disposables.add(disposable)
        }*/


/*

    //未使用包装方法
idList.map { it ->
    request.getJob(it)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<Job> {
            lateinit var dis: Disposable
            override fun onComplete() {
                dis.dispose()
            }

            override fun onSubscribe(d: Disposable) {
                dis = d
            }

            override fun onNext(t: Job) {
                t.isCollected = true
                view.onAddItem(t)
                println("${jobList.size}job~SIZE~id${idList.size}")
            }

            override fun onError(e: Throwable) {
                println("~~~Error~~~" + e.message)
                dis.dispose()
            }
        })
}*/


/*        Flowable.create(FlowableOnSubscribe<Any> { e -> e.onNext("asdasd") }, BackpressureStrategy.ERROR)
            .subscribe(object : Subscriber<Any> {
                override fun onComplete() {
                }

                override fun onSubscribe(s: Subscription?) {
                }

                override fun onNext(t: Any?) {

                }

                override fun onError(t: Throwable?) {
                }
            })*/

