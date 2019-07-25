package com.example.factory.presenter.main

import com.example.factory.data.Job
import com.example.factory.net.model.RspModel

interface IMainView {

    fun addJobList(list:List<Job>)

    fun refreshJobList(list:List<Job>)

    fun onTriggerCollect(job: Job)
}