package com.example.stage2_uikit

import android.app.Application
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.factory.net.NetWorkManager
import com.example.factory.utils.SharedPreferencesUtil

open class MyApplication: Application() {


    companion object {
        /*instance object*/
        @Volatile
        private var INSTANCE: MyApplication? = null

        /*get instance*/
        val instance: MyApplication
            get() {
                if (INSTANCE == null) {
                    synchronized(MyApplication::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE =
                                MyApplication()
                        }
                    }
                }
                return INSTANCE!!
            }
    }

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtil.getInstance(this,"local")
        NetWorkManager.instance.init()
    }

    /**
     * show a Toast
     *
     * @param msg string
     */
    fun showToast(msg: String) {


                Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show()


    }

    /**
     * show a Toast
     *
     * @param msgId msg resource
     */
    fun showToast(@StringRes msgId: Int) {
        showToast(instance.getString(msgId))
    }

}