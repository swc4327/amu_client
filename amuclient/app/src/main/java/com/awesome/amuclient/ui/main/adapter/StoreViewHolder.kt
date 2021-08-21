package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.Store
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_store.view.*

class StoreViewHolder(itemView: View, private val itemClick: (Store) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(store : Store, requestManager : RequestManager) {
        itemView.store_name.text = store.name
        requestManager.load(store.image).circleCrop().into(itemView.store_image)
        itemView.point.text = store.point!!.toFloat().toString()
        itemView.count.text = "("+ store.count.toString() + ")"

        itemView.setOnClickListener { itemClick(store)}
    }
}