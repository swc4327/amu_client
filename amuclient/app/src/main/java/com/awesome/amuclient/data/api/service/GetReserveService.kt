package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.ReserveResponse
import retrofit2.Call
import retrofit2.http.*

interface GetReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getReserveListC")
    fun getReserveList(@Query("client_id") uid:String) : Call<ReserveResponse>
}