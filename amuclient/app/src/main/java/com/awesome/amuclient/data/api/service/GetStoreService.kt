package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.StoreListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetStoreService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getStoreForClient")
    fun getStore(@Query("lat") lat: String, @Query("lng") lng: String, @Query("kind") kind: String) : Call<StoreListResponse>
}