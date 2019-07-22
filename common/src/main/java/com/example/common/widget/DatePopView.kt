package com.example.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.common.R
import java.text.SimpleDateFormat
import java.util.*

class DatePopView(context: Context) : BottomDialogView(context){

    @SuppressLint("InflateParams")
    override var bView: View = LayoutInflater.from(context).inflate(R.layout.datepickerview,null)

    var years = ""
    var months = ""
    var days = ""

    private lateinit var wheelView1: WheelView
    private lateinit var wheelView2: WheelView
    private lateinit var wheelView3: WheelView


    private lateinit var listener: OnMyClickListener

    private lateinit var btnFinish: TextView
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val date = getCurrentDate()

        years = date.get(Calendar.YEAR).toString()+"年"
        months = date.get(Calendar.MONTH).toString()+"月"
        days = date.get(Calendar.DATE).toString()+"日"



        btnFinish = bView.findViewById(R.id.btn_finish)
        btnFinish.setOnClickListener {
            listener.onFinishClick()
        }
        //加载年月日的三个 CalendarView 的 id
        wheelView1 = bView.findViewById(R.id.year) as WheelView
        wheelView2 = bView.findViewById(R.id.month) as WheelView
        wheelView3 = bView.findViewById(R.id.day) as WheelView

        //定义滚动选择器的数据项（年月日的）
        val gradeYear = mutableListOf<String>()
        val gradeMonth = mutableListOf<String>()
        val gradeDay = mutableListOf<String>()

        //为数据项赋值
        val thisYear = Integer.parseInt(SimpleDateFormat("yyyy").format(java.util.Date()))
        for (i in 1980..thisYear)
        //从1980到今年
            gradeYear.add(i.toString() + "年")
        for (i in 1..12)
        // 1月到12月
            gradeMonth.add(i.toString() + "月")
        for (i in 1..31)
        // 1日到31日
            gradeDay.add(i.toString() + "日")

        //set data for wheel
        wheelView1.setData(gradeYear)
        wheelView2.setData(gradeMonth)
        wheelView3.setData(gradeDay)

        wheelView1.setSelected(years)
        wheelView2.setSelected(months)
        wheelView3.setSelected(days)


        //wheel select event
        wheelView1.setOnSelectListener(object : WheelView.OnSelectListener {
            override fun onSelect(text: String) {
                years = text
            }

        })
        wheelView2.setOnSelectListener(object : WheelView.OnSelectListener {
            override fun onSelect(text: String) {
                months = text
            }

        })
        wheelView3.setOnSelectListener(object : WheelView.OnSelectListener {
            override fun onSelect(text: String) {
                days = text
            }

        })


    }




    fun setOnClickListener(listener: OnMyClickListener){
        this.listener = listener
    }

    interface OnMyClickListener {
        fun onFinishClick()
    }

    private fun getCurrentDate():Calendar{

        return Calendar.getInstance()

    }



}