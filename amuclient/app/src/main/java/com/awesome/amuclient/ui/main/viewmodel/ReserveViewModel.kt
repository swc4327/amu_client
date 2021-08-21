package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Reserve
import com.awesome.amuclient.data.model.ReserveList
import com.awesome.amuclient.data.model.remote.ReserveApi

class ReserveViewModel(private var clientId: String) : ViewModel() {
    private val reserveApi = ReserveApi()
    val reserveLists = MutableLiveData<ArrayList<ReserveList>>()
    private val reservesTemp = ArrayList<Reserve>()

    val status = MutableLiveData<Int>()

    fun getReserveList(lastId : String) {
        reserveApi.getReserveList(reserveLists, reservesTemp, clientId, lastId)
    }

    fun addReserve(reserve: Reserve) {
        reserveApi.addReserve(status, reserve)
    }

}