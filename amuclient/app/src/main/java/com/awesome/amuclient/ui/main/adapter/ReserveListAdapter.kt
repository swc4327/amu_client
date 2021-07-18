package com.awesome.amuclient.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.ReserveList

import com.bumptech.glide.RequestManager

class ReserveListAdapter(private val reserveLists : ArrayList<ReserveList>,
                         private val requestManager : RequestManager)
    : RecyclerView.Adapter<ReserveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reserve, parent, false)
        return ReserveListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reserveLists.size
    }

    override fun onBindViewHolder(holder: ReserveListViewHolder, position: Int) {
        holder.bind(reserveLists[position], requestManager)
    }

    fun update(reserveLists: ArrayList<ReserveList>) {
        if(this.reserveLists.isNotEmpty())
            this.reserveLists.clear()
        this.reserveLists.addAll(reserveLists)
        notifyDataSetChanged()
    }
}
