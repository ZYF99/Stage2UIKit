package com.example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val layoutId:Int


    //create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId,container,false)
    }


    //do when view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBefore()
        initWidget()
        initData()
    }


    //something init before initWidget
    open fun initBefore(){


    }


    //widget init
    abstract fun initWidget()


    //data init
    open fun initData(){

    }


}