package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.response.ReserveResponse
import com.awesome.amuclient.data.model.Reserve
import retrofit2.Call
import retrofit2.http.*

interface ReserveService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @GET("/getReserveListC")
    fun getReserveList(@Query("clientId") clientId :String, @Query("lastId") lastId : String) : Call<ReserveResponse>

    @POST("reserve")
    fun addReserve(@Body params: Reserve) : Call<DefaultResponse>

}