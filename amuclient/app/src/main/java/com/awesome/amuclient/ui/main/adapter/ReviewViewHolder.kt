package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.ReviewList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewViewHolder(itemView: View, private val itemClick: (ReviewList)->Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(reviewList: ReviewList, requestManager: RequestManager) {

        itemView.client_name.text = reviewList.client.nickname
        requestManager.load(reviewList.client.image).circleCrop().into(itemView.client_image)
        requestManager.load(reviewList.review.review_image).into(itemView.review_image)
        itemView.comment.text = reviewList.review.comment
        itemView.ratingBar.rating = reviewList.review.point!!.toFloat()
        itemView.time.text = reviewList.review.time
        itemView.setOnClickListener{ itemClick(reviewList)}


    }
}