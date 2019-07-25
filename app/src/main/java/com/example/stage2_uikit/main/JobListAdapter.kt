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

class JobListAdapter(private val context: Context, private var list: MutableList<Job>, val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val BASEVIEW = 0
    private val NEWVIEW = 1

    //the last stationImage current position
    private var oldStationPage = -1


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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder as MyBaseViewHolder

        holder.btnKeep.setOnClickListener {
            listener.onCollectBtnClick(position)
        }

        if (item.isCollected) {
            holder.btnKeep.setBackgroundResource(R.drawable.bg_btn_keep_true)
        } else {
            holder.btnKeep.setBackgroundResource(R.drawable.bg_btn_keep_false)
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

        holder.imEmp.setImageResource(imgId)
        holder.tvPhras.text = item.phrase
        holder.tvOccgName.text = item.occ_g.name + "(" + item.occ.name + ")"
        holder.tvSal.text = item.sal_txt
        holder.tvPref.text = item.pref.name + item.city.name + item.addr + item.addr_etc
        var s = ""
        item.station.map { s += it.name }
        holder.tvStationName.text = s

        holder.tvPubend.text = "[掲載終了日]${item.pubend_dat}"

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



        when (holder) {
            //base type item
            is ViewHolderOfBase -> {
                holder.tvTitle.text = item.txt_title + item.txt
                holder.tvSub.text = item.sub.name + "(" + item.master.name + ")"
            }
            //new type item
            is ViewHolderOfNew -> {

                holder.tvTxtTitle.text = item.txt_title
                holder.tvTxt.text = item.txt

                holder.tvOption.text = item.option
                holder.tvSub.text = "　　 　    　   ${item.sub.name + "(" + item.master.name + ")"}"

                //workstation block
                oldStationPage = -1
                val stationAdapter = ImagePagerAdapter(context, item.img, holder.stationPager)
                holder.stationPager.adapter = stationAdapter
                //photography banner
                //jobList with fragment
                val list = item.img_pr.map {
                    PhotographyFragment(it)
                }
                val adapterPhotography = PhotographyAdapter(context, list)
                holder.photographyPager.adapter = adapterPhotography
                //station block
                //init stationBtn
                if (item.img.size <= 1) holder.btnStationNext.visibility = View.INVISIBLE
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

                //pr block
                //init PrBtn
                holder.btnPrLast.visibility = View.INVISIBLE
                if (item.img_pr.size <= 1) holder.btnPrNext.visibility = View.INVISIBLE
                //pr btn click event -> last
                holder.btnPrNext.setOnClickListener {
                    //check if it can click next
                    if (holder.photographyPager.currentItem + 1 <= item.img_pr.size - 1) {
                        holder.photographyPager.currentItem += 1
                        holder.btnPrLast.visibility = View.VISIBLE
                        //check if it has more, no:hide the btnNext
                        if (holder.photographyPager.currentItem + 1 > item.img_pr.size - 1) {
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
            list[position].new_flg == "1" -> NEWVIEW
            else -> BASEVIEW
        }
    }

    class ViewHolderOfNew(itemView: View) : MyBaseViewHolder(itemView) {

        val tvTxtTitle: TextView by bindView(R.id.tv_txt)
        val tvTxt: TextView by bindView(R.id.tv_txt)
        val tvOption: TextView by bindView(R.id.tv_option)
        val tvSub: TextView by bindView(R.id.tv_sub)


        val stationPager: ViewPager by bindView(R.id.cell_new_pager_workstation)
        val btnStationNext: TextView by bindView(R.id.btn_station_next)
        val photographyPager: ViewPager by bindView(R.id.cell_new_pager_photography)

        val btnPrLast: ImageView by bindView(R.id.btn_photography_last)
        val btnPrNext: ImageView by bindView(R.id.btn_photography_next)

    }

    class ViewHolderOfBase(itemView: View) : MyBaseViewHolder(itemView) {

        val tvTitle: TextView by bindView(R.id.base_tv_title)
        val tvSub: TextView by bindView(R.id.base_tv_sub)


    }

    open class MyBaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

        val btnKeep: LinearLayout by bindView(R.id.btn_keep)

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