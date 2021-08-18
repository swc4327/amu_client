package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.StoreForPromotionResponse
import com.awesome.amuclient.data.api.response.StoreListResponse
import com.awesome.amuclient.data.api.response.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StoreService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @GET("/getStoreForClient")
    fun getStore(@Query("lat") lat: String, @Query("lng") lng: String, @Query("kind") kind: String) : Call<StoreListResponse>

    @GET("/getStoreInfo")
    fun getStoreInfo(@Query("store_id") store_id:String) : Call<StoreResponse>

    @GET("/getStoreForPromotion")
    fun getStoreForPromotion(@Query("client_id") client_id:String) : Call<StoreForPromotionResponse>
}