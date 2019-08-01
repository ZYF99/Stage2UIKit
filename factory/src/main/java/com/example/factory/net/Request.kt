package com.example.factory.net

import com.example.factory.data.Job
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface Request{

    companion object{
        const val HOST = "http://192.168.100.99:8080/v1/"
    }

    @GET("jobs?")
    fun getJobList(@Query("offset")offset:Int):Single<List<Job>>


    @GET("job?")
    fun getJob(@Query("jobId")Id:String):Single<Job>

}