package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.model.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientApi {

    fun addClient(client: Client, status: MutableLiveData<Int>) {

        val joinApi = RetrofitObject.clientService


        joinApi.addClient(client)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("retrofit add client", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("JoinInfoActivity", "success")
                            status.value = 200

                        } else {
                            Log.e("JoinInfoActivity", "실패")
                        }
                    }
                })
}
}