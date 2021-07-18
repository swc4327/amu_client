package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.PromotionListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetPromotionListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getPromotionList")
    fun getPromotionList(@Query("store_id") store_id:String) : Call<PromotionListResponse>
}