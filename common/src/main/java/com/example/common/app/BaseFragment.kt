package com.example.common.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.factory.presenter.BasePresenter

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
    abstract val presenter: BasePresenter

    //create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }


    //do when view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBefore()
        initWidget(view)
        initData()
    }


    //something init before initWidget
    open fun initBefore() {
    }


    //widget init
    abstract fun initWidget(view: View)


    //data init
    open fun initData() {

    }

    //hide keyBoard
    fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (imm!!.isActive)
            imm.hideSoftInputFromWindow(
                (context as Activity).currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

    }


}