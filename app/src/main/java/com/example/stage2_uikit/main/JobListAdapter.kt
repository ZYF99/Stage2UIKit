package com.example.stage2_uikit.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.common.adapter_pager.ImagePagerAdapter
import com.example.factory.data.Job
import com.example.stage2_uikit.R
import kotterknife.bindView

class JobListAdapter(context: Context, private var list: MutableList<Job>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val BASEVIEW = 0
    private val NEWVIEW = 1

    //the last stationImage currentposition
    private var oldStationPage = -1

    private var context: Context? = context


    //ViewHolder Create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWVIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_mainlist_new, parent, false)
                ViewHolderOfNew(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_mainlist_base, parent, false)
                ViewHolderOfBase(view)
            }
        }
    }

    //Data binding
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (holder) {
            //base type item
            is ViewHolderOfBase -> {

            }
            //new type item
            is ViewHolderOfNew -> {
                oldStationPage = -1

                //workstation banner
                val stationAdapter = ImagePagerAdapter(context!!, item.imgList_workstation, holder.stationPager)
                holder.stationPager.adapter = stationAdapter

                //photography banner
                //list with fragment and Triple
                val list = item.list_photography.map {
                    Pair(PhotographyFragment(it), Triple(it.first, it.second, it.third))
                }

                val adapterPhotography = PhotographyAdapter(context!!, list)
                holder.photographyPager.adapter = adapterPhotography


                //station block**************************************************************************
                //init stationBtn
                if (item.imgList_workstation.size <= 1) holder.btnStationNext.visibility = View.INVISIBLE
                //workstation btnNext click event
                holder.btnStationNext.setOnClickListener {

                    //do workstation imageList loop
                    val current = holder.stationPager.currentItem
                    when {

                        current > oldStationPage -> {
                            when {
                                current + 1 > item.imgList_workstation.size - 1 -> holder.stationPager.currentItem =
                                    current - 1
                                else -> holder.stationPager.currentItem = current + 1
                            }
                        }
                        else -> {
                            when {
                                current - 1 < 0 -> holder.stationPager.currentItem = current + 1
                                else -> holder.stationPager.currentItem = current - 1
                            }
                        }
                    }
                    oldStationPage = current
                }

                //pr block*******************************************************************************
                //init PrBtn
                holder.btnPrLast.visibility = View.INVISIBLE
                if (item.list_photography.size <= 1) holder.btnPrNext.visibility = View.INVISIBLE

                //pr btn click event -> last
                holder.btnPrNext.setOnClickListener {
                    //check if it can click next
                    if (holder.photographyPager.currentItem + 1 <= item.list_photography.size - 1) {
                        holder.photographyPager.currentItem += 1
                        holder.btnPrLast.visibility = View.VISIBLE
                        //check if it has more, no:hide the btnNext
                        if (holder.photographyPager.currentItem + 1 > item.list_photography.size - 1) {
                            holder.btnPrNext.visibility = View.INVISIBLE
                        }
                    }
                }

                //pr btn click event -> next
                holder.btnPrLast.setOnClickListener {
                    //check if it has last
                    if (holder.photographyPager.currentItem - 1 >= 0) {
                        holder.photographyPager.currentItem -= 1
                        holder.btnPrNext.visibility = View.VISIBLE
                        //check if it has last, no:hide the btnLast
                        if (holder.photographyPager.currentItem - 1 < 0) {
                            holder.btnPrLast.visibility = View.INVISIBLE
                        }
                    }
                }

            }
        }
    }

    //Getting item types based on filter:isNew
    override fun getItemViewType(position: Int): Int {
        return when {
            list[position].isNew -> NEWVIEW
            else -> BASEVIEW
        }
    }

    class ViewHolderOfNew(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_txt_title: TextView by bindView(R.id.tv_txt)
        val tv_txt: TextView by bindView(R.id.tv_txt)
        val tv_option: TextView by bindView(R.id.tv_option)
        val im_emp: ImageView by bindView(R.id.im_emp)
        val tv_phras: TextView by bindView(R.id.tv_phrase)
        val tv_occg_name: TextView by bindView(R.id.tv_occg_name)
        val tv_sal: TextView by bindView(R.id.tv_sal)
        val tv_pref: TextView by bindView(R.id.tv_pref)
        val tv_station_name: TextView by bindView(R.id.tv_station_name)

        val stationPager: ViewPager by bindView(R.id.cell_new_pager_workstation)
        val btnStationNext: TextView by bindView(R.id.btn_station_next)
        val photographyPager: ViewPager by bindView(R.id.cell_new_pager_photography)

        val btnPrLast: ImageView by bindView(R.id.btn_photography_last)
        val btnPrNext: ImageView by bindView(R.id.btn_photography_next)

    }

    class ViewHolderOfBase(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var pagerStation: ViewPager = itemView.findViewById(R.id.cell)
        //lateinit var pager_photography: ViewPager
    }

    override fun getItemCount(): Int {
        return list.size
    }
}