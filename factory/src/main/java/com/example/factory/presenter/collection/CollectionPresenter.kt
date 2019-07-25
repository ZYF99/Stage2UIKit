package com.example.factory.presenter.collection

import android.annotation.SuppressLint

import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.net.model.runDisRx
import com.example.factory.net.model.runRx
import com.example.factory.presenter.BasePresenter
import com.example.factory.utils.SharedPreferencesUtil
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Cancellable
import io.reactivex.observers.DisposableObserver

import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


class CollectionPresenter(val view: ICollectionView) : BasePresenter(), ICollection {

    val jobList = mutableListOf<Job>()
    private var idList = mutableListOf<String>()

    @SuppressLint("CheckResult")
    override fun refreshList() {

        val request = NetWorkManager.getRequest()

        idList = SharedPreferencesUtil.getListData("collections", String::class.java)


/*
        Observable.fromArray(idList).subscribe(object : Observer<String> {

            lateinit var disposable: Disposable

            override fun onComplete() {
                println("全部发送完成")
                      disposable.dispose()
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: String) {
                request.getJob(t).subscribe {
                    view.onAddItem(it)
                    println("jobList size ${jobList.size}～～～～idListSize${idList.size}")
                }
            }

            override fun onError(e: Throwable) {
                println("发送异常~~~${e.message}")
                       disposable.dispose()
            }


        })*/


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

        }


/*        idList.map { it ->
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


    }


    override fun deleteItem(pos: Int) {
        idList.removeAt(pos)
        SharedPreferencesUtil.putListData("collections", idList)
        view.onRemove(jobList[pos])
    }





}