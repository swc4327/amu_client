package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.ClientResponse
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.model.Client
import retrofit2.Call
import retrofit2.http.*

interface ClientService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @POST("client")
    fun addClient(@Body params: Client) : Call<DefaultResponse>

    @GET("/getClient")
    fun getClient(@Query("clientId") clientId:String) : Call<ClientResponse>
}