package com.awesome.amuclient.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.awesome.amuclient.R
import com.awesome.amuclient.model.Promotion
import kotlinx.android.synthetic.main.promotionlist_item.view.*

class PromotionListAdapter(val context: Context, val promotions : ArrayList<Promotion>): BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.promotionlist_item, null)
        //Log.e("MenuListAdapter Check", "ok")

        view.store_name.setText(promotions[position].store_name)
        view.message.setText(promotions[position].message)
        view.date.setText(promotions[position].date)

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    fun getItemStoreId(position: Int): Any {
        return promotions[position].store_id!!
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return promotions.size
    }
}