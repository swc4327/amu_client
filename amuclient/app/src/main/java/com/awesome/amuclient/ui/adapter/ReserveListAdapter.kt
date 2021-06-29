package com.awesome.amuclient.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.awesome.amuclient.R
import com.awesome.amuclient.model.Reservation
import com.awesome.amuclient.ui.ReserveDetailActivity
import com.awesome.amuclient.ui.ReviewActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_reserve.view.*

class ReserveListAdapter(val context: Context, val reservations: ArrayList<Reservation>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_reserve, null)

        view.reserve_time.setText(reservations[p0].reserve.date)


        Glide
            .with(context)
            .load(reservations[p0].store.image)
            .circleCrop()
            .into(view.store_image)

        view.store_name.setText(reservations[p0].store.name)
        
        view.show_detail.setOnClickListener {
            val intent = Intent(context, ReserveDetailActivity::class.java)
            intent.putExtra("store_name", reservations[p0].store.name)
            intent.putExtra("store_place", reservations[p0].store.place)
            intent.putExtra("store_place_detail", reservations[p0].store.place_detail)
            intent.putExtra("date", reservations[p0].reserve.date)
            intent.putExtra("lat", reservations[p0].store.lat!!)
            intent.putExtra("lng", reservations[p0].store.lng!!)
            context.startActivity(intent)
        }


        ///*****
        view.go_review_act.setOnClickListener {
            if(reservations[p0].reserve.is_reviewed != "1"){
                val intent = Intent(context, ReviewActivity::class.java)
                intent.putExtra("name", reservations[p0].store.name)
                intent.putExtra("store_id", reservations[p0].store.id.toString())
                intent.putExtra("reserve_id", reservations[p0].reserve.id.toString())
                context.startActivity(intent)
            }
            else{
                Toast.makeText(context, "이미 등록된 리뷰입니다.", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reservations.size
    }
}