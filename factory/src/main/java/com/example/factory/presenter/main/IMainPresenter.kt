package com.example.factory.presenter.main

import com.example.factory.data.Job


interface IMainPresenter {

    fun getJobList(offset:Int)

    fun collectJob(job:Job)



}