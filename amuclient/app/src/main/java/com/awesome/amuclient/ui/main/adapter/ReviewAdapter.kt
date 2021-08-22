package com.awesome.amuclient.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.ReviewList
import com.bumptech.glide.RequestManager

class ReviewAdapter(private val reviewLists : ArrayList<ReviewList>,
                    private val requestManager : RequestManager,
                    private val itemClick: (ReviewList) -> Unit)
    : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return reviewLists.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewLists[position], requestManager)
    }

    fun clearReviewLists() {
        if(this.reviewLists.isNotEmpty()) {
            this.reviewLists.clear()
            notifyDataSetChanged()
        }
    }

    fun getLastReviewId(position: Int) : String {
        return this.reviewLists[position].review.id.toString()
    }

    fun update(reviewLists: ArrayList<ReviewList>) {
        val endPosition = this.reviewLists.size
        loadMore(reviewLists, endPosition)

    }

    private fun loadMore(reviewLists: ArrayList<ReviewList>, endPosition: Int) {
        this.reviewLists.addAll(reviewLists)
        notifyItemRangeInserted(endPosition, this.reviewLists.size - endPosition)
    }
}

























//class ReviewAdapter(val context: Context, val reviewLists : ArrayList<ReviewList>): BaseAdapter() {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//        val view : View = LayoutInflater.from(context).inflate(R.layout.reviewlist_item, null)
//
//        if(reviewLists != null) {
//            Glide
//                .with(context)
//                .load(reviewLists[position].review.review_image)
//                .into(view.review_image)
//
//            view.comment.setText(reviewLists[position].review.comment)
//
//            view.ratingBar.rating = reviewLists[position].review.point!!.toFloat()
//
//            view.time.setText(reviewLists[position].review.time)
//
//            Glide
//                .with(context)
//                .load(reviewLists[position].client.image)
//                .circleCrop()
//                .into(view.client_image)
//
//            view.client_name.setText(reviewLists[position].client.nickname)
//            //notifyDataSetChanged()
//        }
//        return view
//    }
//
//    fun getReview(position: Int) : Review {
//        return reviewLists[position].review
//    }
//
//    fun getClient(position: Int) : Client {
//        return reviewLists[position].client
//    }
//
//    override fun getItem(position: Int): Any {
//        return 0
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getCount(): Int {
//        return reviewLists.size
//    }
//
//}