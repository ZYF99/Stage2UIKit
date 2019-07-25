package com.example.stage2_uikit.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.common.adapter_pager.ViewPagerAdapter
import com.example.stage2_uikit.R


class PhotographyAdapter(
    override val context: Context,
    override val list: List<PhotographyFragment>
) : ViewPagerAdapter(context, list) {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = list[position].photography

        val root = LayoutInflater.from(context).inflate(R.layout.page_cell_photography,container,false)
        println("prCount${list.size}")


        Glide.with(context).load(item.url).into(root.findViewById(R.id.cell_photography_img))
        root.findViewById<TextView>(R.id.cell_photography_name).text = item.txt_title

        //set title
        container.addView(root)
        return root
    }

}