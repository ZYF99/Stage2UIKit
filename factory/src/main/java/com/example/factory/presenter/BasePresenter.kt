package com.example.factory.presenter

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter {

    var disposables = CompositeDisposable()

    open fun onDestroy() {
        disposables.clear()
    }
}