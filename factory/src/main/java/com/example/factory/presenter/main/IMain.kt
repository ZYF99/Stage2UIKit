package com.example.factory.presenter.main

import com.example.factory.data.Job
import com.example.factory.presenter.IBase
import io.reactivex.Single


interface IMain {

    interface Presenter{
        fun getJobListRx(offset:Int): Single<List<Job>>

        fun collectJobRx(job: Job): Single<Job>

    }
    interface View :IBase.View{

    }


}