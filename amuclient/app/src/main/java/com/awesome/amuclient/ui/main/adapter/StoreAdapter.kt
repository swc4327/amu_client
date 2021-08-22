package com.awesome.amuclient.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.Store
import com.bumptech.glide.RequestManager


class StoreAdapter(private val stores : ArrayList<Store>,
                   private val requestManager : RequestManager,
                   private val itemClick: (Store) -> Unit)
    : RecyclerView.Adapter<StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(stores[position], requestManager)
    }

    fun getLastStoreId(position : Int) : String {
        return this.stores[position].id.toString()
    }

    fun clearStores() {
        if(this.stores.isNotEmpty()) {
            this.stores.clear()
            notifyDataSetChanged()
        }
    }

    fun update(stores: ArrayList<Store>) {
        val endPosition = this.stores.size
        loadMore(stores, endPosition)

    }

    private fun loadMore(
        stores: ArrayList<Store>,
        endPosition: Int
    ) {
        this.stores.addAll(stores)
        notifyItemRangeInserted(endPosition, this.stores.size - endPosition)
    }
}