package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.MenuResponse
import com.awesome.amuclient.data.model.Constants.FIRST_CALL
import com.awesome.amuclient.data.model.Menu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuApi {

    fun getMenu(
        menus: MutableLiveData<ArrayList<Menu>>,
        storeId: String,
        lastId : String
    ) {

        val joinApi = RetrofitObject.menuService

        joinApi.getMenuList(storeId.toString(), lastId)
            .enqueue(object : Callback<MenuResponse> {

                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    Log.e("Menu Retrofit getMenu", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<MenuResponse>,
                    response: Response<MenuResponse>
                ) {
                    println(response)
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Get menu Retrofit", "success")
                        menus.value = response.body()!!.menus
                    } else {

                    }
                }
            })
    }
}