package com.example.factory.presenter.collection

import com.example.factory.data.Job
import io.reactivex.Single
import io.reactivex.SingleObserver


interface ICollection {

    interface View{
        fun onRemove(job: Job)
    }
    interface Presenter{

        fun refreshListRx(singleObserver: SingleObserver<List<Job>>):Single<List<Job>>

        fun deleteItem(pos:Int)
    }


}