package com.example.stage2_uikit.second

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.common.BaseFragment
import com.example.common.widget.SexBox
import kotterknife.bindView
import com.example.stage2_uikit.R
import com.example.common.widget.DatePopView


class SecondFragment : BaseFragment(), SexBox.OnBoxItemClickListener, View.OnClickListener {


    lateinit var rootView: View

    var sex = SexBox.MAN

    val inputName: TextView by bindView(R.id.edit_name)
    val inputPetName: TextView by bindView(R.id.edit_petname)
    val tvDate: TextView by bindView(R.id.tv_date)
    val sexBox: SexBox by bindView(R.id.sexbox)
    val inputTel: EditText by bindView(R.id.edit_tel)
    val inputEmail: EditText by bindView(R.id.edit_email)
    val tvWork: TextView by bindView(R.id.tv_work)
    val tvCity: TextView by bindView(R.id.tv_city)
    val inputRoad: TextView by bindView(R.id.edit_road)
    val inputRemark: TextView by bindView(R.id.edit_remark)


    override val layoutId: Int = R.layout.fragment_second


    override fun initWidget(view: View) {
        tvDate.setOnClickListener(this)
        tvCity.setOnClickListener(this)
        tvWork.setOnClickListener(this)
        rootView = view
    }


    //SexBox item click event
    override fun onManClick() {
        hideKeyboard()
        sex = SexBox.MAN
    }
    override fun onWomanClick() {
        hideKeyboard()
        sex = SexBox.WOMAN
    }


    //click events
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(p0: View?) {
        p0!!.isFocusableInTouchMode = true
        p0.requestFocus()
        when (p0!!.id) {
            R.id.tv_date -> {
                hideKeyboard()
                rootView?.let { createDatePop(it) }
            }
            R.id.tv_city -> {
                hideKeyboard()
                rootView?.let { createCityPop(it) }
            }
            R.id.tv_work -> {
                hideKeyboard()
            }
            else -> {

            }
        }
        p0.isFocusableInTouchMode = false

    }


    //create pop of datePicker
    private fun createDatePop(view: View) {

        val pop = context?.let { DatePopView(it) }

        pop?.show()

        //pop click listener
        pop?.setOnClickListener(object : DatePopView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                pop.hide()
                tvDate.text = "${pop.years}${pop.months}${pop.days}"
            }
        })

    }

    //create pop of cityPicker
    private fun createCityPop(view: View){


    }
}

