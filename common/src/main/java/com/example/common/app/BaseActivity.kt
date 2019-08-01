package com.example.common.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity(){

    //get current layout id
    abstract val contentLayoutId: Int
    abstract val presenter: com.example.factory.presenter.BasePresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化内部界面
        initWindows()
    }
    //initAll
    private fun initWindows() {
            //get id and set it to layout
            val layId = contentLayoutId
            setContentView(layId)
            initBefore()
            initWidget()
            initData()
    }
    //init something before initWidget
    open fun initBefore() {

    }
    //init widget
    open fun initWidget(){

    }
    //init data after initWidget
    open fun initData() {

    }

    //destroy
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }




}
