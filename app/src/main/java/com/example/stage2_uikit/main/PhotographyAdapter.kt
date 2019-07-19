package com.example.stage2_uikit.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.common.BaseFragment
import com.example.common.adapter_pager.ViewPagerAdapter
import com.example.stage2_uikit.R

class PhotographyAdapter(
    override val context: Context,
    override val list: List<Pair<BaseFragment, Triple<String, String, String>>>
) : ViewPagerAdapter<Triple<String, String, String>>(context, list) {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = list[position]
        val root = LayoutInflater.from(context).inflate(R.layout.page_cell_photography,container,false)
        val imgView = root.findViewById<ImageView>(R.id.cell_new_photography_img)
        Glide.with(context).load(item.second.first).crossFade().into(imgView)
        container.addView(root)
        return root
    }

}