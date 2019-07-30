package com.example.factory.presenter.main

import com.example.factory.data.Job
import io.reactivex.Observable


interface IMain {

    interface Presenter{
        fun getJobListRx(offset:Int): Observable<List<Job>>

        fun collectJobRx(job: Job): Observable<Job>

        fun intervalTest()
    }
    interface View{

    }


}