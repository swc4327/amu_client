package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.StoreListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetKaraokeListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getKaraoke")
    fun getKaraokeList(@Query("lat") lat: String, @Query("lng") lng: String) : Call<StoreListResponse>
}