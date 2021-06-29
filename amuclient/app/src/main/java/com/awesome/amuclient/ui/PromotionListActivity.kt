package com.awesome.amuclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.awesome.amuclient.R
import com.awesome.amuclient.api.Constants
import com.awesome.amuclient.api.response.PromotionListResponse
import com.awesome.amuclient.api.response.StoreForPromotionResponse
import com.awesome.amuclient.api.response.StoreResponse
import com.awesome.amuclient.api.service.GetPromotionListService
import com.awesome.amuclient.api.service.GetStoreForPromotionService
import com.awesome.amuclient.api.service.GetStoreInfoService
import com.awesome.amuclient.model.Promotion
import com.awesome.amuclient.model.Store
import com.awesome.amuclient.model.StoreId
import com.awesome.amuclient.ui.adapter.PromotionListAdapter
import com.awesome.amuclient.util.FirebaseUtils
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_promotion_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PromotionListActivity : AppCompatActivity() {

    private var promotions : ArrayList<Promotion> = ArrayList<Promotion>()
    private var promotionListAdapter: PromotionListAdapter? = null

    private var store_ids : ArrayList<StoreId> = ArrayList<StoreId>()
    private var store = Store(null,"","","","","","","","",null,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotion_list)

        close_promotion_list.setOnClickListener {
            finish()
        }



        getStoreForPromotion()

        promotion_list.setOnItemClickListener { parent, view, position, id ->
            var storeId = promotionListAdapter!!.getItemStoreId(position).toString()
            getStoreInfo(storeId)
        }
    }

    private fun getStoreForPromotion() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val joinApi = retrofit.create(GetStoreForPromotionService::class.java)

        joinApi.getStoreForPromotion(FirebaseUtils.getUid())
                .enqueue(object : Callback<StoreForPromotionResponse> {

                    override fun onFailure(call: Call<StoreForPromotionResponse>, t: Throwable) {
                        Log.e("get store for promotion", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<StoreForPromotionResponse>,
                            response: Response<StoreForPromotionResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("get store for promotion", "success")

                            store_ids.addAll(response.body()!!.store_ids)
                            getPromotionList()
                        } else {

                        }
                    }
                })
    }

    private fun getPromotionList() {

        if(store_ids.size != 0) {
            for (i in 0..store_ids.size - 1) {
                val gson = GsonBuilder().setLenient().create()
                val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.serverUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                val joinApi = retrofit.create(GetPromotionListService::class.java)

                joinApi.getPromotionList(store_ids[i].store_id.toString())
                        .enqueue(object : Callback<PromotionListResponse> {

                            override fun onFailure(call: Call<PromotionListResponse>, t: Throwable) {
                                Log.e("get promotion list", "실패")
                                Log.e("Check", t.toString())
                            }

                            override fun onResponse(
                                    call: Call<PromotionListResponse>,
                                    response: Response<PromotionListResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                                    Log.e("get promotion list", "success")

                                    promotions.addAll(response.body()!!.promotions)
                                    promotionListAdapter = PromotionListAdapter(
                                            this@PromotionListActivity, promotions
                                    )
                                    promotion_list.adapter = promotionListAdapter

                                } else {

                                }
                            }
                        })
            }
        }
    }

    private fun getStoreInfo(storeId: String) {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val joinApi = retrofit.create(GetStoreInfoService::class.java)

        joinApi.getStoreInfo(storeId)
                .enqueue(object : Callback<StoreResponse> {

                    override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                        Log.e("get store info", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<StoreResponse>,
                            response: Response<StoreResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("get store info", "success")
                            store = response.body()!!.store!!
                            val intent = Intent(this@PromotionListActivity, StoreInfoActivity::class.java)
                            intent.putExtra("name", store.name)
                            intent.putExtra("manager_uid", store.manager_uid)
                            intent.putExtra("store_id", store.id.toString())
                            intent.putExtra("lat", store.lat)
                            intent.putExtra("lng", store.lng)
                            intent.putExtra("place_detail", store.place_detail)
                            intent.putExtra("place", store.place)
                            intent.putExtra("point", store.point.toString())
                            intent.putExtra("count", store.count.toString())
                            startActivity(intent)
                        } else {

                        }
                    }
                })
    }
}