package com.awesome.amuclient.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.awesome.amuclient.R
import com.awesome.amuclient.api.Constants
import com.awesome.amuclient.api.response.ReserveListResponse
import com.awesome.amuclient.api.response.StoreResponse
import com.awesome.amuclient.api.service.GetReserveListService
import com.awesome.amuclient.api.service.GetStoreInfoService
import com.awesome.amuclient.model.*
import com.awesome.amuclient.ui.adapter.ReserveListAdapter
import com.awesome.amuclient.util.FirebaseUtils
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_reserve_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReserveListActivity : AppCompatActivity() {

    private var reserves: ArrayList<Reserve> = ArrayList<Reserve>()
    private var stores: ArrayList<Store> = ArrayList<Store>()
    private var reserveListAdapter: ReserveListAdapter? = null
    //private var reservations : ArrayList<Reservation> = ArrayList<Reservation>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)

        close_reserve_list.setOnClickListener {
            finish()
        }
        getReserveList()

//        reserve_list.setOnItemClickListener { parent, view, position, id ->
//            val intent = Intent(this, ReserveDetailActivity::class.java)
//            intent.putExtra("store_name", reserveListAdapter!!.getItemStoreName(position).toString())
//            intent.putExtra("store_place", reserveListAdapter!!.getItemStorePlace(position).toString())
//            intent.putExtra("store_place_detail", reserveListAdapter!!.getItemStorePlaceDetail(position).toString())
//            intent.putExtra("date", reserveListAdapter!!.getItemDate(position).toString())
//
//            intent.putExtra("lat", reserveListAdapter!!.getItemLat(position).toString())
//            intent.putExtra("lng", reserveListAdapter!!.getItemLng(position).toString())
//            startActivity(intent)
//        }

    }

    private fun getReserveList() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.serverUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val joinApi = retrofit.create(GetReserveListService::class.java)
        val uid = FirebaseUtils.getUid()


        joinApi.getReserveList(uid)
                .enqueue(object : Callback<ReserveListResponse> {

                    override fun onFailure(call: Call<ReserveListResponse>, t: Throwable) {
                        Log.e("get reserve list", "실패")
                        Log.e("Check", t.toString())
                    }

                    override fun onResponse(
                            call: Call<ReserveListResponse>,
                            response: Response<ReserveListResponse>
                    ) {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            reserves.addAll(response.body()!!.reserves)
                            getStoreInfo(reserves)
                        } else {

                        }
                    }
                })
    }

    private fun getStoreInfo(reserves: ArrayList<Reserve>) {
        val storeIds = this.reserves.map { it.store_id }.distinct()
        val disposable = Observable.just(storeIds)
                .concatMapIterable { it }
                .concatMap { storeId -> getStore(storeId) }
                .toList()
                .map { stores ->
                    val reservations : ArrayList<Reservation> = ArrayList<Reservation>()
                    //val reservations : ArrayList<Reservation> = ArrayList<Reservation>()
                    for (reserve in reserves) {
                        val store = stores.find { it.id.toString() == reserve.store_id }
                        val reservation = store?.let { Reservation(it, reserve) }
                        reservation?.let { reservations.add(it) }
                    }
                    reservations
                }
                .subscribe({ reservations ->
                    reservations.reverse()
                    reserveListAdapter = ReserveListAdapter(this, reservations)
                    reserve_list.adapter = reserveListAdapter

                }, {

                })
    }

    private fun getStore(storeId: String): Observable<Store> {
        return Observable.create { emitter ->
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.serverUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            val joinApi = retrofit.create(GetStoreInfoService::class.java)
            joinApi.getStoreInfo(storeId)
                    .enqueue(object : Callback<StoreResponse> {

                        override fun onFailure(
                                call: Call<StoreResponse>,
                                t: Throwable
                        ) {
                            emitter.onError(t)
                        }

                        override fun onResponse(
                                call: Call<StoreResponse>,
                                response: Response<StoreResponse>
                        ) {
                            if (response.body() != null) {
                                emitter.onNext(response.body()!!.store)
                                emitter.onComplete()
                            }
                        }
                    })
        }
    }
}