package com.example.stage2_uikit.second

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.common.app.BaseFragment
import com.example.common.utils.JudgeUtil
import com.example.common.widget.*
import com.example.factory.presenter.BasePresenter
import com.example.factory.presenter.IBase
import com.example.stage2_uikit.R
import kotterknife.bindView


class SecondFragment : BaseFragment(), SexBox.OnBoxItemClickListener, View.OnClickListener, IBase.View {
    override val presenter = BasePresenter(this)

    private val btnDateDown: ImageView by bindView(R.id.btndatedown)
    private val btnJobDown: ImageView by bindView(R.id.btnjobdown)
    private val btnCityDown: ImageView by bindView(R.id.btncitydown)

    private val tvNameError: TextView by bindView(R.id.error_name)
    private val tvPetNameError: TextView by bindView(R.id.error_petname)
    private val tvDateError: TextView by bindView(R.id.error_date)
    private val tvTelError: TextView by bindView(R.id.error_tel)
    private val tvEmailError: TextView by bindView(R.id.error_email)
    private val tvJobError: TextView by bindView(R.id.error_job)
    private val tvCityError: TextView by bindView(R.id.error_city)

    private val inputName: TextView by bindView(R.id.edit_name)
    private val inputPetName: TextView by bindView(R.id.edit_petname)
    val tvDate: TextView by bindView(R.id.tv_date)
    val sexBox: SexBox by bindView(R.id.sexbox)
    private val inputTel: EditText by bindView(R.id.edit_tel)
    private val inputEmail: EditText by bindView(R.id.edit_email)
    private val tvJob: TextView by bindView(R.id.tv_work)
    private val tvCity: TextView by bindView(R.id.tv_city)
    val inputRoad: TextView by bindView(R.id.edit_road)
    val inputRemark: TextView by bindView(R.id.edit_remark)
    private val btnSubmit: TextView by bindView(R.id.btn_submit)

    private var sex = SexBox.MAN

    override val layoutId: Int = R.layout.fragment_second

    override fun initWidget(view: View) {
        tvDate.setOnClickListener(this)
        tvCity.setOnClickListener(this)
        tvJob.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
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
        when (p0.id) {
            R.id.tv_date -> {

                createDatePop()
            }
            R.id.tv_city -> {

                createCityPop()
            }
            R.id.tv_work -> {
                createJobPop()
            }
            R.id.btn_submit -> {
                submit()
            }
            else -> {

            }
        }
        p0.isFocusableInTouchMode = false

    }


    //create pop of datePicker
    private fun createDatePop() {
        hideKeyboard()
        btnDateDown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { DatePopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                tvDate.text = pop.years + pop.months + pop.days
            }
        })

    }

    //create pop of jobPicker
    private fun createJobPop() {
        hideKeyboard()
        btnJobDown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { JobPopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                tvJob.text = pop.job
            }
        })

    }

    //create pop of cityPicker
    private fun createCityPop() {
        hideKeyboard()
        btnCityDown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { CityPopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                tvCity.text = pop.city
            }
        })
    }

    //do submit
    private fun submit() {
        judge()
    }

    //judge input
    private fun judge() {
        if (inputName.text.isEmpty()) {
            showError(inputName, tvNameError)
        } else {
            hideError(inputName, tvNameError)
        }
        if (inputPetName.text.isEmpty()) {
            showError(inputPetName, tvPetNameError)
        } else {
            hideError(inputPetName, tvPetNameError)
        }
        if (tvDate.text.isEmpty()) {
            showError(tvDate, tvDateError)
        } else {
            hideError(tvDate, tvDateError)
        }
        if (inputTel.text.isEmpty() || inputTel.length() < 10) {
            showError(inputTel, tvTelError)
        } else {
            hideError(inputTel, tvTelError)
        }
        if (inputEmail.text.isEmpty() || !(JudgeUtil.isEmail(inputEmail.text.toString()))) {
            showError(inputEmail, tvEmailError)
        } else {
            hideError(inputEmail, tvEmailError)
        }
        if (tvJob.text.isEmpty()) {
            showError(tvJob, tvJobError)
        } else {
            hideError(tvJob, tvJobError)
        }
        if (tvCity.text.isEmpty()) {
            showError(tvCity, tvCityError)
        } else {
            hideError(tvCity, tvCityError)
        }
    }

    private fun showError(inputView: View, errorTextView: TextView) {
        inputView.background = context?.resources!!.getDrawable(R.drawable.bg_inputlin_error)
        errorTextView.visibility = View.VISIBLE
    }

    private fun hideError(inputView: View, errorTextView: TextView) {
        inputView.background = context?.resources!!.getDrawable(R.drawable.bg_inputlin_idle)
        errorTextView.visibility = View.INVISIBLE
    }


}

