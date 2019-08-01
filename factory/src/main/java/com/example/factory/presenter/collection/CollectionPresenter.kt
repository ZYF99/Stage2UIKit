package com.example.factory.presenter.collection

import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.presenter.BasePresenter
import com.example.factory.utils.SharedPreferencesUtil
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CollectionPresenter(private val mView: ICollection.View) : BasePresenter(mView), ICollection.Presenter {


    val jobList = mutableListOf<Job>()
    var idList = mutableListOf<String>()


    @SuppressLint("CheckResult")
    override fun getJobList(): Single<List<Job>> {

        //get whole List by Rxjava（toList）
        idList = SharedPreferencesUtil.getListData("collections", String::class.java)

        val request = NetWorkManager.getRequest()

        return Observable.fromIterable(idList).flatMapSingle {
            request.getJob(it)
        }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteItem(pos: Int) {
        idList.removeAt(pos)
        SharedPreferencesUtil.putListData("collections", idList)
        mView.onRemove(jobList[pos])
    }
}

