package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.MenuResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MenuService {
    @Headers("accept: application/json",
            "content-type: application/json")
    @GET("/getMenuList")
    fun getMenuList(@Query("store_id") store_id:String, @Query("lastId") lastId:String) : Call<MenuResponse>
}