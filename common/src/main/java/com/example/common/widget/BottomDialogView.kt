package com.example.common.widget

import android.app.AlertDialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.example.common.R

abstract class BottomDialogView(context: Context) : AlertDialog(context, R.style.MyDialog) {

    abstract var bView: View

    private var bIsCanCancel: Boolean = false
    private var bIsBackCancelable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(bView)//这行一定要写在前面
        setCancelable(bIsCanCancel)//点击外部不可dismiss
        setCanceledOnTouchOutside(bIsBackCancelable)
        val window = this.window
        window?.setGravity(Gravity.BOTTOM)
        val params = window?.attributes
        params!!.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = params
    }


}