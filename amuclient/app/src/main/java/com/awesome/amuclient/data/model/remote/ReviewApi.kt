package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.model.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewApi {

    fun addReview(
        status: MutableLiveData<Int>,
        review: Review
    ) {

        val joinApi = RetrofitObject.addReviewService


        joinApi.addReview(review)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("add review", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("add review", "success")
                            status.value = 200

                        } else {
                            Log.e("add review", "실패")
                        }
                    }
                })
    }
    fun getReview() {

    }
}