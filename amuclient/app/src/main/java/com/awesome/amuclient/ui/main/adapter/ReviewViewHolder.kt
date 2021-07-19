package com.awesome.amuclient.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.data.model.ReviewList
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val clientName = itemView.client_name
    private val clientImage = itemView.client_image
    private val reviewImage = itemView.review_image
    private val reviewComment = itemView.comment
    private val reviewRating = itemView.ratingBar
    private val reviewTime = itemView.time

    fun bind(reviewList: ReviewList, requestManager: RequestManager) {
        clientName.text = reviewList.client.nickname
        requestManager.load(reviewList.client.image).circleCrop().into(clientImage)
        requestManager.load(reviewList.review.review_image).into(reviewImage)
        reviewComment.text = reviewList.review.comment
        reviewRating.rating = reviewList.review.point!!.toFloat()
        reviewTime.text = reviewList.review.time
    }
}