package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.StoreForPromotionResponse
import retrofit2.Call
import retrofit2.http.*

interface GetStoreForPromotionService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getStoreForPromotion")
    fun getStoreForPromotion(@Query("client_id") client_id:String) : Call<StoreForPromotionResponse>
}