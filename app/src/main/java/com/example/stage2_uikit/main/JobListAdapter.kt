package com.example.stage2_uikit.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.common.adapter_pager.ImagePagerAdapter
import com.example.factory.data.Job
import com.example.stage2_uikit.R
import kotterknife.bindView

class JobListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val BASEVIEW = 0
    private val NEWVIEW = 1

    //the last image currentposition
    var oldStationPage = -1
    var oldPhotographyPage = -1

    var list: MutableList<Job> = mutableListOf()
    var context: Context? = null

    constructor(context: Context, list: MutableList<Job>) {
        this.list = list
        this.context = context
    }


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
            is ViewHolderOfBase -> {

            }
            is ViewHolderOfNew -> {

                oldPhotographyPage = -1
                oldStationPage = -1
                //workstation block , just image
                val stationAdapter = ImagePagerAdapter(context!!, item.imgList_workstation, holder.stationPager)
                holder.stationPager.adapter = stationAdapter

                //photography block
                //list with fragment and Triple
                val list = item.list_photography.map {

                    Pair(PhotographyFragment(it), Triple(it.first, it.second, it.third))
                }

                val adapterPhotography = PhotographyAdapter(context!!, list)
                holder.photographyPager.adapter = adapterPhotography

                //workstation nextBtn clickevent, image list loop
                holder.btn_station_next.setOnClickListener {
                    //do imageList loop
                    doLoop(holder,0,item.imgList_workstation.size)
                }

                holder.btnPrNext.setOnClickListener {
                    doLoop(holder,1,item.list_photography.size)
                }

                holder.btnPrLast.setOnClickListener {
                    doLoop(holder,1,item.list_photography.size)
                }


            }
        }
    }


    private fun doLoop(holder: ViewHolderOfNew,stationOrPr:Int,listSize:Int) {
        when{
            //workstation
            stationOrPr == 0 ->{
                val current = holder.stationPager.currentItem
                when {
                    current > oldStationPage -> {
                        when {
                            current + 1 >= listSize - 1 -> holder.stationPager.currentItem = current - 1
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
            //pr
            stationOrPr == 1 -> {
                val current = holder.photographyPager.currentItem
                when {
                    current > oldPhotographyPage -> {
                        when {
                            current + 1 >= listSize - 1 -> holder.photographyPager.currentItem = current - 1
                            else -> holder.photographyPager.currentItem = current + 1
                        }
                    }
                    else -> {
                        when {
                            current - 1 < 0 -> holder.photographyPager.currentItem = current + 1
                            else -> holder.photographyPager.currentItem = current - 1
                        }
                    }
                }
                oldPhotographyPage = current
            }

        }

    }


    //Getting item types based on filter:isNew
    override fun getItemViewType(position: Int): Int {
        when {
            list[position].isNew -> return NEWVIEW
            !list[position].isNew -> return BASEVIEW
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolderOfNew(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_txt_title: TextView = itemView.findViewById(R.id.tv_txt)
        val tv_txt: TextView = itemView.findViewById(R.id.tv_txt)
        val tv_option: TextView = itemView.findViewById(R.id.tv_option)
        val im_emp: ImageView = itemView.findViewById(R.id.im_emp)
        val tv_phras: TextView = itemView.findViewById(R.id.tv_phrase)
        val tv_occg_name: TextView = itemView.findViewById(R.id.tv_occg_name)
        val tv_sal: TextView = itemView.findViewById(R.id.tv_sal)
        val tv_pref: TextView = itemView.findViewById(R.id.tv_pref)
        val tv_station_name: TextView = itemView.findViewById(R.id.tv_station_name)

        val stationPager: ViewPager by bindView(R.id.cell_new_pager_workstation)
        val btn_station_next: TextView by bindView(R.id.btn_station_next)
        val photographyPager: ViewPager by bindView(R.id.cell_new_pager_photography)

        val btnPrLast: ImageView by bindView(R.id.btn_photography_last)
        val btnPrNext: ImageView by bindView(R.id.btn_photography_next)


    }

    class ViewHolderOfBase(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var pagerStation: ViewPager = itemView.findViewById(R.id.cell)
        //lateinit var pager_photography: ViewPager
    }


}