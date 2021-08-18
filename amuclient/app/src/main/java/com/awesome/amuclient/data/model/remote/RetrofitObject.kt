package com.awesome.amuclient.data.model.remote

import com.awesome.amuclient.data.api.service.*
import com.awesome.amuclient.data.model.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    val storeService : StoreService = retrofit.create(StoreService::class.java)
    val reviewService : ReviewService = retrofit.create(ReviewService::class.java)
    val reserveService : ReserveService = retrofit.create(ReserveService::class.java)
    val menuService : MenuService = retrofit.create(MenuService::class.java)
    val promotionService : PromotionService = retrofit.create(PromotionService::class.java)
    val clientService : ClientService = retrofit.create(ClientService::class.java)

}