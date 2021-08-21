package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.StoresResponse
import com.awesome.amuclient.data.api.response.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StoreService {
    @Headers("accept: application/json",
            "content-type: application/json")

    @GET("/getStoreForClient")
    fun getStore(@Query("lat") lat: String,
                 @Query("lng") lng: String,
                 @Query("kind") kind: String,
                 @Query("lastId") lastId: String) : Call<StoresResponse>

    @GET("/getStoreInfo")
    fun getStoreInfo(@Query("storeId") storeId:String) : Call<StoreResponse>

    @GET("/getVisitedStore")
    fun getVisitedStore(@Query("clientId") clientId:String) : Call<StoresResponse>
}