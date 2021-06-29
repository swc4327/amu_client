package com.awesome.amuclient.api.service

import com.awesome.amuclient.api.response.MenuListResponse
import retrofit2.Call
import retrofit2.http.*

interface GetMenuListService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("/getMenuList")
    fun getMenuList(@Query("store_id") store_id:String) : Call<MenuListResponse>
}