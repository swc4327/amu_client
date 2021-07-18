package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Client
import com.awesome.amuclient.data.model.remote.ClientApi

class ClientViewModel : ViewModel() {
    private val clientApi = ClientApi()
    val status = MutableLiveData<Int>()

    fun addClient(client : Client) {
        clientApi.addClient(client, status)
    }
}