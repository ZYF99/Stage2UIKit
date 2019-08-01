package com.example.factory.presenter

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//offered to presenter Layer's access with compositeDisposable
interface IRx {
    val compositeDisposable: CompositeDisposable
        get() = CompositeDisposable()
}

//Rx function
@SuppressLint("CheckResult")
fun <T> runRxSingle(subscribeOnIo: Boolean, single: Single<T>): Single<T> {
    return when (subscribeOnIo) {
        true -> single
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
        false -> single
    }

}

@SuppressLint("CheckResult")
fun <T> Single<T>.bindLife(compositeDisposable: CompositeDisposable) {
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    compositeDisposable.add(this.subscribe({}, { e ->
        println("~~~Error~~~" + e.message)
    }))
}




