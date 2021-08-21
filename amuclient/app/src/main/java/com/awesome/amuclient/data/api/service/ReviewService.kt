package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.response.ReviewResponse
import com.awesome.amuclient.data.model.Review
import retrofit2.Call
import retrofit2.http.*

interface ReviewService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @GET("/getReviewList")
    fun getReviewList(@Query("storeId") storeId:String, @Query("lastId") lastId:String) : Call<ReviewResponse>

    @POST("/addReview")
    fun addReview(@Body params: Review) : Call<DefaultResponse>
}