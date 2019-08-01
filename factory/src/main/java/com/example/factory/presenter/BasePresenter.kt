package com.example.factory.presenter




open class BasePresenter(var view: IBase.View?) : IBase.Presenter {



    open fun onDestroy() {
        view = null
        compositeDisposable.clear()
    }





}