package com.awesome.amuclient.data.api.service

import com.awesome.amuclient.data.api.response.MenuResponse
import retrofit2.Call
import retrofit2.http.*

interface GetMenuService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getMenuList")
    fun getMenuList(@Query("store_id") store_id:String) : Call<MenuResponse>
}