package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.StoreListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetBowlingListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getBowling")
    fun getBowlingList(@Query("lat") lat: String, @Query("lng") lng: String) : Call<StoreListResponse>
}