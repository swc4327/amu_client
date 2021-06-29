package com.awesome.amuclient.ui.storeinfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.amuclient.R
import com.awesome.amuclient.api.Constants
import com.awesome.amuclient.api.response.ClientResponse
import com.awesome.amuclient.api.response.ReviewListResponse
import com.awesome.amuclient.api.service.GetClientInfoService
import com.awesome.amuclient.api.service.GetReviewListService
import com.awesome.amuclient.model.*
import com.awesome.amuclient.ui.adapter.ReviewListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var reviews : ArrayList<Review> = ArrayList<Review>()
    private var client = Client("","","","","")
    private var reviewListAdapter: ReviewListAdapter? = null
    private var storeId: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_review, container, false)

        auth = FirebaseAuth.getInstance()
        storeId = arguments?.getString("store_id")
        getReviewList()

        return view
    }


    private fun getReviewList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.serverUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val joinApi = retrofit.create(GetReviewListService::class.java)
        Log.e("store_id check", storeId.toString())

        joinApi.getReviewList(storeId.toString())
            .enqueue(object : Callback<ReviewListResponse> {

                override fun onFailure(call: Call<ReviewListResponse>, t: Throwable) {
                    Log.e("Retrofit GetReview", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<ReviewListResponse>,
                    response: Response<ReviewListResponse>
                )  {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Get ReviewList Retrofit" , "success")

                        reviews.addAll(response.body()!!.reviews)
                        getClientInfo()

                    } else {

                    }
                }
            })
    }
    private fun getClientInfo() {
        val clientIds = this.reviews.map { it.client_id }.distinct()
        val disposable = Observable.just(clientIds)
            .concatMapIterable { it }
            .concatMap { clientId -> getClient(clientId) }
            .toList()
            .map { clients ->
                val reviewLists : ArrayList<ReviewList> = ArrayList<ReviewList>()
                for (review in reviews) {
                    val client = clients.find { it.uid == review.client_id }
                    val reviewList = client?.let { ReviewList(it, review) }
                    reviewList?.let { reviewLists.add(it) }
                }
                reviewLists
            }
            .subscribe({ reviewLists ->
                reviewListAdapter = ReviewListAdapter(requireContext(), reviewLists)
                review_list.adapter = reviewListAdapter

            }, {

            })
    }

    private fun getClient(clientId: String): Observable<Client> {
        return Observable.create { emitter ->
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val joinApi = retrofit.create(GetClientInfoService::class.java)
            joinApi.getClient(clientId)
                .enqueue(object : Callback<ClientResponse> {

                    override fun onFailure(
                        call: Call<ClientResponse>,
                        t: Throwable
                    ) {
                        emitter.onError(t)
                    }

                    override fun onResponse(
                        call: Call<ClientResponse>,
                        response: Response<ClientResponse>
                    ) {
                        if (response.body() != null) {
                            emitter.onNext(response.body()!!.client)
                            emitter.onComplete()
                        }
                    }
                })
        }
    }
}







//Log.e("getClientInfo","start")
//var client = client
////var reviewListAdapter = reviewListAdapter
//if(reviews.size != 0) {
//    for (i in 0..reviews.size - 1) {
//        val gson = GsonBuilder().setLenient().create()
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Constants.serverUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//        val joinApi = retrofit.create(GetClientInfoService::class.java)
//
//        Log.e("client id check", reviews[i].client_id.toString())
//        joinApi.getClient(reviews[i].client_id.toString())
//            .enqueue(object : Callback<ClientResponse> {
//
//                override fun onFailure(
//                    call: Call<ClientResponse>,
//                    t: Throwable
//                ) {
//                    Log.e("Retrofit GetClient", "실패")
//                    Log.e("Check", t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<ClientResponse>,
//                    response: Response<ClientResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Get Client Retrofit", "success")
//                        client = response.body()!!.client!!
//                        Log.e("client name check", client.nickname.toString())
//                        reviewListAdapter =
//                            ReviewListAdapter(
//                                requireContext(), reviews,
//                                client!!
//                            )
//                        reviewList.adapter = reviewListAdapter
//                    } else {
//
//                    }
//                }
//            })
//    }
//}