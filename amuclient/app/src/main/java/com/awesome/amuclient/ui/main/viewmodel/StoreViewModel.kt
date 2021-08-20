package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Store
import com.awesome.amuclient.data.model.remote.StoreApi

class StoreViewModel() : ViewModel() {
    private val storeApi = StoreApi()
    val stores = MutableLiveData<ArrayList<Store>>()
    private var storesTemp = ArrayList<Store>()
    val status = MutableLiveData<Int>()

    fun getStore(lat : String?, lng: String?, kind: String, lastId : String) {
        storeApi.getStore(stores, storesTemp, lat!!, lng!!, kind, lastId)
    }

    fun getVisitedStore(clientId : String) {
        storeApi.getVisitedStore(clientId, stores, storesTemp)
    }
}