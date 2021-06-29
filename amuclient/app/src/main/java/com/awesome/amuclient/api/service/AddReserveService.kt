package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.DefaultResponse
import com.awesome.amuclient.model.Reserve
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddReserveService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("reserve")
    fun addReserve(@Body params: Reserve) : Call<DefaultResponse>
}