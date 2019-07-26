package com.example.stage2_uikit.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.common.adapter_pager.ImagePagerAdapter
import com.example.factory.data.Job
import com.example.stage2_uikit.R
import kotterknife.bindView

class JobListAdapter(private val context: Context, private var list: MutableList<Job>, private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //the last stationImage current position
    private var oldStationPage = -1

    //ViewHolder Create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_jobmainlist, parent, false)
        return MyViewHolder(view)
    }

    //Data binding
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder as MyViewHolder
        when (item.new_flg) {
            "0" -> {
                //not new
                holder.linNew.visibility = View.GONE
                holder.tvBaseTitle.visibility = View.VISIBLE
                holder.tvBaseTitle.text = item.txt_title + item.txt
            }
            "1" -> {
                //new
                holder.linNew.visibility = View.VISIBLE
                holder.tvBaseTitle.visibility = View.GONE
                holder.tvNewTitle.text = item.txt_title
                holder.tvNewTxt.text = item.txt
                println("itemtxttitle~~" + item.txt_title)
            }
        }

        var imgId = 100
        when (item.emp.id) {
            "100" -> {
                imgId = R.drawable.icn_job_regular
            }
            "110" -> {
                imgId = R.drawable.icn_job_fresh
            }
            "120" -> {
                imgId = R.drawable.icn_job_part
            }
            "130" -> {
                imgId = R.drawable.icn_job_dispatch
            }
            "140" -> {
                imgId = R.drawable.icn_job_intro
            }
            "150" -> {
                imgId = R.drawable.icn_job_delegation
            }
            "160" -> {
                imgId = R.drawable.icn_job_agreement
            }
            "170" -> {
                imgId = R.drawable.icn_job_irregular
            }
            "180" -> {
                imgId = R.drawable.icn_job_agency
            }
            "199" -> {
                imgId = R.drawable.icn_job_other
            }
        }

        holder.tvOption.text = item.option

        var subText = ""
        if (item.option.isNotEmpty()) {
            for (i in 0..item.option.length) {
                subText += "    "
            }
        }

        holder.tvSub.text = "$subText${item.sub.name + "(" + item.master.name + ")"}"

        holder.imEmp.setImageResource(imgId)
        holder.tvPhras.text = item.phrase
        holder.tvOccgName.text = item.occ_g.name + "(" + item.occ.name + ")"
        holder.tvSal.text = item.sal_txt
        holder.tvPref.text = item.pref.name + item.city.name + item.addr + item.addr_etc
        var s = ""
        item.station.map { s += it.name }
        holder.tvStationName.text = s
        val datArr = item.pubend_dat.split("-".toRegex())
        holder.tvPubend.text = "[掲載終了日]${datArr[0]}年${datArr[1]}月${datArr[2]}日"

        when {
            item.pubend_days == "0" -> {
                holder.linPubend.visibility = View.GONE
            }
            item.pubend_days == "" -> {
                holder.tvEndtody.visibility = View.GONE
                holder.linPubend.visibility = View.GONE
            }
            item.pubend_days.toInt() >= 1 -> {
                holder.tvEndtody.visibility = View.GONE
                holder.tvPubendDay.text = item.pubend_days
            }
        }

        //workstation block
        if (item.img.isEmpty()) {
            holder.linStation.visibility = View.GONE
        } else {
            holder.linStation.visibility = View.VISIBLE
            oldStationPage = -1
            val stationAdapter = ImagePagerAdapter(context, item.img, holder.stationPager)
            holder.stationPager.adapter = stationAdapter
            //init stationBtn
            if (item.img.size <= 1) {
                holder.btnStationNext.visibility = View.GONE
            } else {
                holder.btnStationNext.visibility = View.VISIBLE
            }
            //workstation btnNext click event
            holder.btnStationNext.setOnClickListener {
                //do workstation imageList loop
                val current = holder.stationPager.currentItem
                when {
                    current > oldStationPage -> {
                        when {
                            current + 1 > item.img.size - 1 -> holder.stationPager.currentItem =
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
        }

        //photography block
        if (item.img_pr.isEmpty()) {
            holder.linPr.visibility = View.GONE
        } else {
            holder.linPr.visibility = View.VISIBLE
            val adapterPhotography = PhotographyAdapter(context, item.img_pr)
            holder.photographyPager.adapter = adapterPhotography
            //init PrBtn
            holder.btnPrLast.visibility = View.INVISIBLE
            if (item.img_pr.size <= 1) holder.btnPrNext.visibility = View.INVISIBLE


            holder.photographyPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                    //check if it has more, no:hide the btnNext
                    if (holder.photographyPager.currentItem + 1 > item.img_pr.size - 1) {
                        holder.btnPrNext.visibility = View.INVISIBLE
                    }else{
                        holder.btnPrNext.visibility = View.VISIBLE
                    }
                    //check if it has last, no:hide the btnLast
                    if (holder.photographyPager.currentItem - 1 < 0) {
                        holder.btnPrLast.visibility = View.INVISIBLE
                    }else{
                        holder.btnPrLast.visibility = View.VISIBLE
                    }
                }

            })


            //pr btn click event -> last
            holder.btnPrNext.setOnClickListener {
                //check if it can click next
                if (holder.photographyPager.currentItem + 1 <= item.img_pr.size - 1) {
                    holder.photographyPager.currentItem += 1
                    holder.btnPrLast.visibility = View.VISIBLE
                }
            }
            //pr btn click event -> next
            holder.btnPrLast.setOnClickListener {
                //check if it has last
                if (holder.photographyPager.currentItem - 1 >= 0) {
                    holder.photographyPager.currentItem -= 1
                    holder.btnPrNext.visibility = View.VISIBLE
                }
            }
        }

        //collect button click event
        holder.btnCollect.setOnClickListener {
            listener.onCollectBtnClick(position)
        }

        if (item.isCollected) {

            holder.tvCollect.text = "キープ中"
            holder.btnCollect.setBackgroundResource(R.drawable.bg_btn_keep_true)
        } else {
            holder.tvCollect.text = "キープする"
            holder.btnCollect.setBackgroundResource(R.drawable.bg_btn_keep_false)
        }
    }


    open class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //new
        val linNew: LinearLayout by bindView(R.id.lin_new)
        val tvNewTitle: TextView by bindView(R.id.new_tv_title)
        val tvNewTxt: TextView by bindView(R.id.new_tv_txt)

        //not new
        val tvBaseTitle: TextView by bindView(R.id.base_tv_title)


        //base
        val tvSub: TextView by bindView(R.id.tv_sub)
        val tvOption: TextView by bindView(R.id.tv_option)
        val imEmp: ImageView by bindView(R.id.im_emp)
        val tvPhras: TextView by bindView(R.id.tv_phrase)
        val tvOccgName: TextView by bindView(R.id.tv_occg_name)
        val tvSal: TextView by bindView(R.id.tv_sal)
        val tvPref: TextView by bindView(R.id.tv_pref)
        val tvStationName: TextView by bindView(R.id.tv_station_name)

        val linPubend: LinearLayout by bindView(R.id.lin_pubend)
        val tvEndtody: TextView by bindView(R.id.tv_endtoday)
        val tvPubendDay: TextView by bindView(R.id.tv_pubend_day)
        val tvPubend: TextView by bindView(R.id.tv_pubend)


        val linStation: LinearLayout by bindView(R.id.lin_station)
        val stationPager: ViewPager by bindView(R.id.cell_pager_workstation)
        val btnStationNext: TextView by bindView(R.id.btn_station_next)

        val linPr: LinearLayout by bindView(R.id.lin_photography)
        val photographyPager: ViewPager by bindView(R.id.cell_pager_photography)
        val btnPrLast: ImageView by bindView(R.id.btn_photography_last)
        val btnPrNext: ImageView by bindView(R.id.btn_photography_next)

        //val imCollect: ImageView by bindView(R.id.im_collect)
        val tvCollect: TextView by bindView(R.id.tv_collect)
        val btnCollect: LinearLayout by bindView(R.id.btn_keep)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(list: Collection<Job>) {

        this.list.addAll(list)
        notifyDataSetChanged()

    }

    fun replaceAll(list: Collection<Job>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(job: Job) {
        this.list.add(job)
        notifyDataSetChanged()
    }

    fun removeItem(job: Job) {
        this.list.remove(job)
        notifyDataSetChanged()
    }

    fun triggerCollectItem(job: Job) {

        job.isCollected = !job.isCollected

        notifyDataSetChanged()
    }

    interface Listener {
        fun onCollectBtnClick(pos: Int)
    }


}