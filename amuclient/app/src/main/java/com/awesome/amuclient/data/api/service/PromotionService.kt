package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.PromotionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PromotionService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @GET("/getPromotionList")
    fun getPromotionList(@Query("store_id") store_id:String) : Call<PromotionResponse>
}