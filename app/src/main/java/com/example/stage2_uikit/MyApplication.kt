package com.example.stage2_uikit

import android.app.Application
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.factory.net.NetWorkManager
import com.example.factory.utils.SharedPreferencesUtil

class MyApplication: Application() {


    companion object {

        @Volatile
        var INSTANCE: MyApplication? = null
        /*instance object*/
        private var instance:MyApplication? = null
        get() {
            if(INSTANCE == null){
                INSTANCE = MyApplication()
            }
            return INSTANCE
        }
        fun get():MyApplication{

            return instance!!
        }

        /**
         * show a Toast
         *
         * @param msg string
         */
        private fun showToast(msg: String) {


            Toast.makeText(get(), msg, Toast.LENGTH_SHORT).show()


        }

        /**
         * show a Toast
         *
         * @param msgId msg resource
         */
        fun showToast(@StringRes msgId: Int) {
            showToast(get().getString(msgId))
        }


        /*get instance*/
/*        private val instance: MyApplication
            get() {
                if (INSTANCE == null) {
                    synchronized(MyApplication::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE =

                        }
                    }
                }
                return INSTANCE!!
            }*/





    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtil.getInstance(this,"local")
        NetWorkManager.instance.init()
    }




}