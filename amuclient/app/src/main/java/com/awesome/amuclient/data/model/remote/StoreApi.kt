package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.StoresResponse
import com.awesome.amuclient.data.model.Constants.FIRST_CALL
import com.awesome.amuclient.data.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreApi {

    fun getStore(stores: MutableLiveData<ArrayList<Store>>,
                 lat: String,
                 lng: String,
                 kind: String,
                 lastId : String) {

        val joinApi = RetrofitObject.storeService

        joinApi.getStore(lat.toString(),lng.toString(), kind, lastId)

            .enqueue(object : Callback<StoresResponse> {

                override fun onFailure(call: Call<StoresResponse>, t: Throwable) {
                    Log.e("getStore", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                        call: Call<StoresResponse>,
                        response: Response<StoresResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        stores.value = response.body()!!.stores

                    } else {

                    }
                }
            })
    }

    fun getVisitedStore(
            clientId: String,
            stores: MutableLiveData<ArrayList<Store>>
    ) {

        val joinApi = RetrofitObject.storeService

        joinApi.getVisitedStore(clientId)
                .enqueue(object : Callback<StoresResponse> {

                    override fun onFailure(call: Call<StoresResponse>, t: Throwable) {
                        Log.e("get store for promotion", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<StoresResponse>,
                            response: Response<StoresResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("get store for promotion", "success")
                            //예약한 기록이 있고, 리뷰를 작성 했는 경우 -> store 추출
                            stores.value = response.body()!!.stores
                        } else {

                        }
                    }
                })
    }
}