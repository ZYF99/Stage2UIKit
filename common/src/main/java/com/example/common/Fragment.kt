package com.example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class Fragment : Fragment() {

    abstract val layoutId:Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(layoutId,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBefore()
        initWidget()
        initdata()
    }

    open fun initBefore(){


    }

    abstract fun initWidget()

    open fun initdata(){

    }


}