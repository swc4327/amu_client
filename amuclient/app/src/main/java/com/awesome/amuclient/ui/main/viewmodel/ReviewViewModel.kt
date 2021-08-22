package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Review
import com.awesome.amuclient.data.model.ReviewList
import com.awesome.amuclient.data.model.remote.ReviewApi

class ReviewViewModel(private val storeId: String?) : ViewModel() {
    private val reviewApi = ReviewApi()
    val reviewLists = MutableLiveData<ArrayList<ReviewList>>()
    val status = MutableLiveData<Int>()

    fun addReview(review : Review) {
        reviewApi.addReview(status, review)
    }

    fun getReviewList(lastId : String) {
        if (storeId != null) {
            reviewApi.getReviewList(reviewLists, storeId, lastId)
        }
    }

}