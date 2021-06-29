package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.ReserveListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReserveListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReserveListC")
    fun getReserveList(@Query("client_id") uid:String) : Call<ReserveListResponse>
}