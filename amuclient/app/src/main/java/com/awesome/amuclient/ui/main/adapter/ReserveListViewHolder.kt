package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.ReserveList
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.ui.main.view.ReserveListActivity
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveListViewHolder(itemView: View,
                            private val itemClick: (Store)->Unit,
                            private val showDetailClick: (ReserveList)->Unit,
                            private val goReviewClick: (ReserveList)->Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(reserveList: ReserveList, requestManager: RequestManager) {
        itemView.store_name.text = reserveList.store.name
        requestManager.load(reserveList.store.image).circleCrop().into(itemView.store_image)
        itemView.reserve_time.text = reserveList.reserve.date

        itemView.setOnClickListener{ itemClick(reserveList.store)}
        itemView.show_detail.setOnClickListener {showDetailClick(reserveList)}
        itemView.go_review.setOnClickListener { goReviewClick(reserveList) }
    }
}