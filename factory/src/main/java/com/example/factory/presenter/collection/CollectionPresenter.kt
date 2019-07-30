package com.example.factory.presenter.collection

import android.annotation.SuppressLint
import com.example.factory.data.Job
import com.example.factory.net.NetWorkManager
import com.example.factory.net.runDisRx
import com.example.factory.net.runRxFromList
import com.example.factory.presenter.BasePresenter
import com.example.factory.utils.SharedPreferencesUtil
import io.reactivex.*


class CollectionPresenter(val view: ICollection.View) : BasePresenter(), ICollection.Presenter {



    val jobList = mutableListOf<Job>()
    private var idList = mutableListOf<String>()


    @SuppressLint("CheckResult")
    override fun refreshListRx(singleObserver: SingleObserver<List<Job>>): Single<List<Job>> {
        val request = NetWorkManager.getRequest()
        idList = SharedPreferencesUtil.getListData("collections", String::class.java)
        //get whole List by Rxjava（toList）
        return runRxFromList({ request.getJob(it) }, idList,singleObserver)
    }

    override fun deleteItem(pos: Int){
        idList.removeAt(pos)
        SharedPreferencesUtil.putListData("collections", idList)
        view.onRemove(jobList[pos])
    }
}

