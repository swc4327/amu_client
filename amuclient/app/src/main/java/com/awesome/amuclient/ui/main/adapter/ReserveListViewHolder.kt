package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.ReserveList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val storeName = itemView.store_name
    private val storeImage = itemView.store_image
    private val reserveTime = itemView.reserve_time

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        storeName.text = reserveList.store.name
        requestManager.load(reserveList.store.image).circleCrop().into(storeImage)
        reserveTime.text = reserveList.reserve.date
    }
}