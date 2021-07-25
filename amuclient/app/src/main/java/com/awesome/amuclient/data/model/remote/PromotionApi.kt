package com.awesome.amuclient.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amuclient.data.api.response.ClientResponse
import com.awesome.amuclient.data.api.response.PromotionListResponse
import com.awesome.amuclient.data.api.response.StoreForPromotionResponse
import com.awesome.amuclient.data.api.response.StoreResponse
import com.awesome.amuclient.data.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionApi {

    fun getStoreForPromotion(
        clientId: String,
        PromotionLists: MutableLiveData<ArrayList<PromotionList>>
    ) {

        var storeIds = ArrayList<StoreId>()

        val joinApi = RetrofitObject.getStoreForPromotionService

        joinApi.getStoreForPromotion(clientId)
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
                            //예약한 기록이 있는 store id 추출
                            println(storeIds.size)
                            println(response.body()!!.storeIds.size)
                            storeIds.addAll(response.body()!!.storeIds)
                            getPromotionList(storeIds, PromotionLists)
                        } else {

                        }
                    }
                })
    }

    private fun getPromotionList(
        storeIds: ArrayList<StoreId>,
        PromotionLists: MutableLiveData<ArrayList<PromotionList>>
    ) {

        var promotionsTemp = ArrayList<Promotion>()

        if(storeIds.size != 0) {
            val joinApi = RetrofitObject.getPromotionListService
            for (i in 0 until storeIds.size) {
                joinApi.getPromotionList(storeIds[i].store_id.toString())
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
                                    //promotions.value = response.body()!!.promotions
                                    promotionsTemp.addAll(response.body()!!.promotions)
                                    getStoreInfo(PromotionLists, promotionsTemp)

                                } else {

                                }
                            }
                        })
            }
        }
    }

    fun getStoreInfo(
        PromotionLists: MutableLiveData<ArrayList<PromotionList>>,
        promotionsTemp: ArrayList<Promotion>
    ) {

        var storesTemp : ArrayList<Store> = ArrayList<Store>()

        val storeIds = promotionsTemp.map { it.id }.distinct()
        val disposable = Observable.just(storeIds)
            .concatMapIterable { it }
            .concatMap { storeId -> getStore(storeId.toString()) }
            .toList()
            .map { stores ->
                storesTemp.addAll(stores)
                val promotionLists : ArrayList<PromotionList> = ArrayList<PromotionList>()
                for (promotion in promotionsTemp) {
                    val store = stores.find { it.id.toString() == promotion.store_id }
                    val promotionList = store?.let { PromotionList(it, promotion) }
                    promotionList?.let { promotionLists.add(it) }
                }
                promotionLists
            }
            .subscribe({ promotionLists ->
                PromotionLists.value = promotionLists

            }, {

            })
    }

    private fun getStore(storeId: String): Observable<Store> {
        return Observable.create { emitter ->
            val joinApi = RetrofitObject.getStoreInfoService
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