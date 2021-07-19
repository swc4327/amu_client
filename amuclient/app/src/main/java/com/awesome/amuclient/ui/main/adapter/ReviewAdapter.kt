package com.awesome.amuclient.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.ReviewList
import com.bumptech.glide.RequestManager

class ReviewAdapter(private val reviewLists : ArrayList<ReviewList>,
                    private val requestManager : RequestManager)
    : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewLists.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewLists[position], requestManager)
    }

    fun update(reviewLists: ArrayList<ReviewList>) {
        if(this.reviewLists.isNotEmpty())
            this.reviewLists.clear()
        this.reviewLists.addAll(reviewLists)
        notifyDataSetChanged()
    }
}


