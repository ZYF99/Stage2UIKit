package com.example.factory.net.model

import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import io.reactivex.Flowable
import io.reactivex.Observable

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/*fun <T> runRx(observable: Observable<T>, subscriber: Subscriber<T>): Subscription =
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)*/


@SuppressLint("CheckResult")
fun <T> runRx(observable: Observable<T>, observer: Observer<T>) =
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)


fun <T> runDisRx(observable: Observable<T>, disposableObserver: DisposableObserver<T>): DisposableObserver<T> {
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(disposableObserver)
    return disposableObserver
}


/*
fun <T>runRxLamda(observer: Observer<T>,next:(T)->Unit,error:(e:Throwable)->Unit,completed:()->Unit={ println("runRxLamda~completed!!")})
        :Subscription{
}
*/

