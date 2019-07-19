package com.example.common


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    //得到当前界面布局ID
    open abstract val contentLayoutId: Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化内部界面
        initWindows()
    }


    private fun initWindows() {
            //得到界面ID并设置到界面中
            val layId = contentLayoutId
            setContentView(layId)
            initBefore()
            initWidget()
            initData()
    }


    //初始化控件调用之前

    open fun initBefore() {

    }

    //初始化控件
    open fun initWidget(){

    }


    //初始化数据
    open fun initData() {

    }

    /*初始化相关参数 参数bundle
    参数正确返回true
    错误返回false*/
    open fun initArgs(bundle: Bundle?): Boolean {
        return true
    }

}
