package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.StoreListResponse
import com.awesome.amuclient.data.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreApi {

    fun getStore(stores: MutableLiveData<ArrayList<Store>>, storesTemp: ArrayList<Store>, lat: String, lng: String, kind: String) {

        val joinApi = RetrofitObject.getStoreService

        joinApi.getStore(lat.toString(),lng.toString(), kind)

            .enqueue(object : Callback<StoreListResponse> {

                override fun onFailure(call: Call<StoreListResponse>, t: Throwable) {
                    Log.e("getStore", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<StoreListResponse>,
                    response: Response<StoreListResponse>
                )  {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        if(storesTemp.isNotEmpty()) {
                            storesTemp.clear()
                        }
                       storesTemp.addAll(response.body()!!.stores)
                        stores.value = storesTemp

                    } else {

                    }
                }
            })
    }

}