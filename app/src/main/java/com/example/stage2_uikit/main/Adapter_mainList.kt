package com.example.stage2_uikit.main

import android.content.Context
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.factory.data.Bean_mainList
import com.example.stage2_uikit.R

class Adapter_mainList : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    val BASE_VIEW = 0
    val NEW_VIEW = 1


    var list: List<Bean_mainList> = listOf()
    var context: Context? = null

    constructor()

    constructor(context: Context, list: List<Bean_mainList>) {
        this.list = list
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            NEW_VIEW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_mainlist_new, parent, false)
                return ViewHolder_new(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_mainlist_base, parent, false)
                return ViewHolder_base(view)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        when {
            list[position].isNew -> return NEW_VIEW
            !list[position].isNew -> return BASE_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    class ViewHolder_new(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

    class ViewHolder_base(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}