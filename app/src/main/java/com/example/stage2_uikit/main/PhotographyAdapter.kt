package com.example.stage2_uikit.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.common.app.BaseFragment
import com.example.common.adapter_pager.ViewPagerAdapter
import com.example.factory.data.Job
import com.example.stage2_uikit.R

class PhotographyAdapter(
    override val context: Context,
    override val list: List<PhotographyFragment>
) : ViewPagerAdapter(context, list) {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val root = LayoutInflater.from(context).inflate(R.layout.page_cell_photography,container,false)

        container.addView(root)
        return root
    }

}