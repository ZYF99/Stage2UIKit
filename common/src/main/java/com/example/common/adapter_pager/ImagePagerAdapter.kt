package com.example.common.adapter_pager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

class ImagePagerAdapter(val context:Context,val imgs :List<String>,val pager:ViewPager) : PagerAdapter() {

    val imgViews = mutableListOf<ImageView>()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = imgs[position%imgs.size]
        val imageView = ImageView(context)

        //存列表
        imgViews.add(imageView)
        //放在pager中
        pager.addView(imageView)

        Glide.with(context).load(item).crossFade().into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        pager.removeView(imgViews[position%imgs.size])
    }


}