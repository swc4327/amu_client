package com.awesome.amuclient.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Promotion
import com.awesome.amuclient.data.model.PromotionList
import com.awesome.amuclient.data.model.Store
import com.bumptech.glide.RequestManager


class PromotionAdapter(private val promotionLists : ArrayList<PromotionList>, private val requestManager : RequestManager, private val itemClick: (Store)->Unit)
    : RecyclerView.Adapter<PromotionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promotion, parent, false)
        return PromotionViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return promotionLists.size
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        holder.bind(promotionLists[position], requestManager)
    }

    fun update(promotionLists: ArrayList<PromotionList>) {
        if(this.promotionLists.isNotEmpty())
            this.promotionLists.clear()
        this.promotionLists.addAll(promotionLists)
        notifyDataSetChanged()
    }
}
