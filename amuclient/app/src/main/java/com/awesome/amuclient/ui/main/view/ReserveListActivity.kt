package com.awesome.amuclient.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.R
import com.awesome.amuclient.data.model.*
import com.awesome.amuclient.ui.main.adapter.ReserveListAdapter
import com.awesome.amuclient.ui.main.viewmodel.FirebaseViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModel
import com.awesome.amuclient.ui.main.viewmodel.ReserveViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reserve_list.*

class ReserveListActivity : AppCompatActivity() {

    private var reserveListAdapter: ReserveListAdapter? = null

    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var reserveViewModel: ReserveViewModel

    private var clientId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_list)

        initListener()

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        clientId = firebaseViewModel.getUid()
        var factory = ReserveViewModelFactory(clientId.toString())
        reserveViewModel = ViewModelProvider(this, factory).get(ReserveViewModel::class.java)


        observe()


        reserveViewModel.getReserveList()
    }

    private fun observe() {
        reserveViewModel.reserveLists.observe(this, Observer<ArrayList<ReserveList>> {reserveLists->
            if(reserveListAdapter == null) {
                reserveListAdapter = ReserveListAdapter(arrayListOf(), Glide.with(this))
                reserve_list.adapter = reserveListAdapter
            }
            reserveListAdapter!!.update(reserveLists)
        })

    }

    private fun initListener() {
        close_reserve_list.setOnClickListener {
            finish()
        }
    }
}

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


//    private fun getReserveList() {
//        val gson = GsonBuilder().setLenient().create()
//        val retrofit = Retrofit.Builder()
//                .baseUrl(Constants.serverUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
//        val joinApi = retrofit.create(GetReserveService::class.java)
//        val uid = firebaseViewModel.getUid()
//
//
//        joinApi.getReserveList(uid)
//                .enqueue(object : Callback<ReserveResponse> {
//
//                    override fun onFailure(call: Call<ReserveResponse>, t: Throwable) {
//                        Log.e("get reserve list", "실패")
//                        Log.e("Check", t.toString())
//                    }
//
//                    override fun onResponse(
//                        call: Call<ReserveResponse>,
//                        response: Response<ReserveResponse>
//                    ) {
//                        println(response)
//                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                            reserves.addAll(response.body()!!.reserves)
//                            getStoreInfo(reserves)
//                        } else {
//
//                        }
//                    }
//                })
//    }
//
//    private fun getStoreInfo(reserves: ArrayList<Reserve>) {
//        val storeIds = this.reserves.map { it.store_id }.distinct()
//        val disposable = Observable.just(storeIds)
//                .concatMapIterable { it }
//                .concatMap { storeId -> getStore(storeId) }
//                .toList()
//                .map { stores ->
//                    val reservations : ArrayList<ReserveList> = ArrayList<ReserveList>()
//                    //val reservations : ArrayList<Reservation> = ArrayList<Reservation>()
//                    for (reserve in reserves) {
//                        val store = stores.find { it.id.toString() == reserve.store_id }
//                        val reservation = store?.let { ReserveList(it, reserve) }
//                        reservation?.let { reservations.add(it) }
//                    }
//                    reservations
//                }
//                .subscribe({ reservations ->
//                    reservations.reverse()
//                    reserveListAdapter = ReserveListAdapter(this, reservations)
//                    reserve_list.adapter = reserveListAdapter
//
//                }, {
//
//                })
//    }
//
//    private fun getStore(storeId: String): Observable<Store> {
//        return Observable.create { emitter ->
//            val gson = GsonBuilder().setLenient().create()
//            val retrofit = Retrofit.Builder()
//                    .baseUrl(Constants.serverUrl)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build()
//
//            val joinApi = retrofit.create(GetStoreInfoService::class.java)
//            joinApi.getStoreInfo(storeId)
//                    .enqueue(object : Callback<StoreResponse> {
//
//                        override fun onFailure(
//                                call: Call<StoreResponse>,
//                                t: Throwable
//                        ) {
//                            emitter.onError(t)
//                        }
//
//                        override fun onResponse(
//                                call: Call<StoreResponse>,
//                                response: Response<StoreResponse>
//                        ) {
//                            if (response.body() != null) {
//                                emitter.onNext(response.body()!!.store)
//                                emitter.onComplete()
//                            }
//                        }
//                    })
//        }
//    }