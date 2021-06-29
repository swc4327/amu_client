package com.awesome.amuclient.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amuclient.R
import com.awesome.amuclient.model.Store
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_store.view.*
import kotlinx.android.synthetic.main.reviewlist_item.view.*

class StoreListAdapter(val context: Context, val stores: ArrayList<Store>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_store, null)

        Glide
            .with(context)
            .load(stores[p0].image)
            .circleCrop()
            .into(view.store_image)

        view.review_count_value.setText(stores[p0].count.toString())

        view.ratingBar_store.rating = stores[p0].point!!

        view.store_name.setText(stores[p0].name)
        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    fun getItemName(position: Int): Any {
        return stores[position].name
    }

    fun getItemManagerUid(position: Int): Any {
        return stores[position].manager_uid
    }

    fun getItemStoreId(position: Int): Any {
        return stores[position].id.toString()
    }

    fun getItemLat(position: Int): Any {
        return stores[position].lat
    }

    fun getItemPoint(position : Int) : Any {
        return stores[position].point!!
    }

    fun getItemLng(position: Int): Any {
        return stores[position].lng
    }

    fun getItemCount(position: Int): Any {
        return stores[position].count!!
    }

    fun getItemPlaceDetail(position: Int): Any {
        return stores[position].place_detail.toString()
    }

    fun getItemPlace(position: Int): Any {
        return stores[position].place.toString()
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return stores.size
    }
}