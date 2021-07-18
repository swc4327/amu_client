package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.model.Client
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("client")
    fun addClient(@Body params: Client) : Call<DefaultResponse>
}