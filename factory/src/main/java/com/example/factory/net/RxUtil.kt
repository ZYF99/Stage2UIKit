package com.example.factory.net

import android.annotation.SuppressLint
import io.reactivex.Observable

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/*fun <T> runRx(observable: Observable<T>, subscriber: Subscriber<T>): Subscription =
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)*/




fun <T> runDisRx(observable: Observable<T>, disposableObserver: DisposableObserver<T>): DisposableObserver<T> {
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(disposableObserver)
    return disposableObserver
}


fun <T> runDisRxList(observable: Observable<T>, disposableObserver: DisposableObserver<T>): DisposableObserver<T> {
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(disposableObserver)
    return disposableObserver
}


//return SingleObserver
@SuppressLint("CheckResult")
fun <T,T1> runRxFromList(func:(T1) -> Observable<T>, list: List<T1>, singleObserver: SingleObserver<List<T>>):SingleObserver<List<T>>{

    Observable.fromIterable(list).flatMap {
        func(it)
    }
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(singleObserver)

    return singleObserver
}


/*Observable.fromIterable(idList)
.flatMap {
    id -> request.getJob(id)
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


