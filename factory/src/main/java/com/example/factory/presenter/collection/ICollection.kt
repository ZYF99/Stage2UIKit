package com.example.factory.presenter.collection

import com.example.factory.data.Job
import com.example.factory.presenter.IBase

import io.reactivex.Single



interface ICollection {

    interface View :IBase.View{
        fun onRemove(job: Job)
    }
    interface Presenter{

        fun getJobList(): Single<List<Job>>

        fun deleteItem(pos:Int)
    }


}