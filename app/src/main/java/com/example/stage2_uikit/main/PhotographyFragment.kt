package com.example.stage2_uikit.main

import android.view.View
import com.bumptech.glide.Glide
import com.example.common.BaseFragment
import com.example.stage2_uikit.R
import kotlinx.android.synthetic.main.page_cell_photography.*

class PhotographyFragment(private val photography:Triple<String,String,String>) : BaseFragment(){
    override val layoutId = R.layout.page_cell_photography

    override fun initWidget(view:View) {
        //load image
        Glide.with(context).load(photography.first).crossFade().into(cell_new_photography_img)
        //set name
        cell_new_photography_name.text = photography.second
        //set brief
        cell_new_photography_brief.text = photography.third
    }
}