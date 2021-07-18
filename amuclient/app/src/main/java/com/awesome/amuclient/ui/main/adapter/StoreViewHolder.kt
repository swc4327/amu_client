package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.Store
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_store.view.*

class StoreViewHolder(itemView: View, private val itemClick: (Store) -> Unit): RecyclerView.ViewHolder(itemView) {

    private val storeName = itemView.store_name
    private val storeImage = itemView.store_image
    private val ratingValue = itemView.point
    private val reviewCount = itemView.count

    fun bind(store : Store, requestManager : RequestManager) {
        storeName.text = store.name
        requestManager.load(store.image).circleCrop().into(storeImage)
        ratingValue.text = store.point!!.toFloat().toString()
        reviewCount.text = "("+ store.count.toString() + ")"

        itemView.setOnClickListener { itemClick(store)}
    }
}