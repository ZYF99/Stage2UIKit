package com.example.stage2_uikit.main

import android.view.View
import com.bumptech.glide.Glide
import com.example.common.app.BaseFragment
import com.example.factory.data.Job
import com.example.factory.presenter.BasePresenter
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.page_cell_photography.*

class PhotographyFragment(private val photography: Job.ImgPR) : BaseFragment() {
    override val presenter = BasePresenter()
    override val layoutId = R.layout.page_cell_photography

    override fun initWidget(view: View) {
        //load image
        Glide.with(context).load(photography.url).crossFade().into(cell_new_photography_img)

        //set name
        //cell_new_photography_name.text = photography.second

        //set title
        cell_new_photography_brief.text = photography.txt_title
    }
}