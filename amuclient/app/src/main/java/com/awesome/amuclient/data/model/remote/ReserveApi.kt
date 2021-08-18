package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.DefaultResponse
import com.awesome.amuclient.data.api.response.ReserveResponse
import com.awesome.amuclient.data.api.response.StoreResponse
import com.awesome.amuclient.data.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveApi {

    fun addReserve(
        status: MutableLiveData<Int>,
        reserve: Reserve
    ) {
        val joinApi = RetrofitObject.reserveService

        joinApi.addReserve(reserve)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("Retrofit Reserve", "실패")
                    Log.e("Check", t.toString())
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("Add Reserve", "success")
                        status.value = 200

                    } else {
                        Log.e("AddReserve", "실패")
                    }
                }
            })
    }

    fun getReserveList(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        clientId: String
    ) {

        var reservesTemp = ArrayList<Reserve>()

        val joinApi = RetrofitObject.reserveService

        joinApi.getReserveList(clientId)
                .enqueue(object : Callback<ReserveResponse> {

                    override fun onFailure(call: Call<ReserveResponse>, t: Throwable) {
                        Log.e("get reserve", "실패")
                    }

                    override fun onResponse(
                            call: Call<ReserveResponse>,
                            response: Response<ReserveResponse>
                    )  {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            reservesTemp.addAll(response.body()!!.reserves)
                            getStoreInfo(ReserveLists, reservesTemp)
                        } else {

                        }
                    }
                })
    }

    private fun getStoreInfo(
        ReserveLists: MutableLiveData<ArrayList<ReserveList>>,
        reservesTemp: ArrayList<Reserve>
    ) {

        var storesTemp : ArrayList<Store> = ArrayList<Store>()

        val storeIds = reservesTemp.map { it.store_id }.distinct()
        val disposable = Observable.just(storeIds)
            .concatMapIterable { it }
            .concatMap { storeId -> getStore(storeId) }
            .toList()
            .map { stores ->
                storesTemp.addAll(stores)
                val reserveLists : ArrayList<ReserveList> = ArrayList<ReserveList>()
                //val reservations : ArrayList<Reservation> = ArrayList<Reservation>()
                for (reserve in reservesTemp) {
                    val store = stores.find { it.id.toString() == reserve.store_id }
                    val reserveList = store?.let { ReserveList(it, reserve) }
                    reserveList?.let { reserveLists.add(it) }
                }
                reserveLists
            }
            .subscribe({ reserveLists ->
                ReserveLists.value = reserveLists
            }, {

            })
    }


    private fun getStore(storeId: String): Observable<Store> {
        return Observable.create { emitter ->
            val joinApi = RetrofitObject.storeService
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