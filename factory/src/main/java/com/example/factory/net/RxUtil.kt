package com.example.factory.net

import android.annotation.SuppressLint
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*fun <T> runRx(observable: Observable<T>, subscriber: Subscriber<T>): Subscription =
    observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)*/


@SuppressLint("CheckResult")
fun <T> runDisRx(observable: Observable<T>, observer: Observer<T>): Observable<T> {
    observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)
    return observable
}



//return SingleObserver
@SuppressLint("CheckResult")
fun <T, T1> runRxFromList(
    func: (T1) -> Observable<T>,
    list: List<T1>,
    singleObserver: SingleObserver<List<T>>
): Single<List<T>> {

    val single = Observable.fromIterable(list).flatMap {
        func(it)
    }
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    single.subscribe(singleObserver)

    return single
}



