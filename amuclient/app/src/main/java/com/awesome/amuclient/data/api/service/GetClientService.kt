package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.ClientResponse
import retrofit2.Call
import retrofit2.http.*

interface GetClientService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getClient")
    fun getClient(@Query("client_id") client_id:String) : Call<ClientResponse>
}