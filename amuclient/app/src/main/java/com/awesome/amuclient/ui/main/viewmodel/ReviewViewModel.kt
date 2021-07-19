package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Review
import com.awesome.amuclient.data.model.remote.ReviewApi

class ReviewViewModel(private val clientId: String?) : ViewModel() {
    private val reviewApi = ReviewApi()
    val reviews = MutableLiveData<ArrayList<Review>>()
    val status = MutableLiveData<Int>()

    fun addReview(review : Review) {
        reviewApi.addReview(status, review)
    }

}