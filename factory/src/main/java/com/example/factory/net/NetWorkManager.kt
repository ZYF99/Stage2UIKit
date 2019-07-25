package com.example.factory.net

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Api init class
 * */

class NetWorkManager {

    companion object {


        private var retrofit: Retrofit? = null

        @Volatile
        private var request: Request? = null


        /*instance object*/
        @Volatile
        private var INSTANCE: NetWorkManager? = null

        /*get instance*/
        val instance: NetWorkManager
            get() {
                if (INSTANCE == null) {
                    synchronized(NetWorkManager::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE =
                                NetWorkManager()
                        }
                    }
                }
                return INSTANCE!!
            }


        fun getRequest(): Request {

            if (request == null) {
                synchronized(Request::class.java) {
                    request = retrofit!!.create(Request::class.java)
                }
            }
            return request!!
        }

    }


    /**
     * init okhttp and retrofit
     * */
    fun init() {

        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            println("RSP_STRING:$it")
        })
        logging.level = HttpLoggingInterceptor.Level.BODY
        //init okhttpClient
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        //init retrofit
        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Request.HOST)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val gson = GsonBuilder().addDeserializationExclusionStrategy(object : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?) = false
        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            return f?.name == "isCollected"
        }
    }
    ).create()


}