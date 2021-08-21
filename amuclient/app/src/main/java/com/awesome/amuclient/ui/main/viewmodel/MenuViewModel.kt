package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Menu
import com.awesome.amuclient.data.model.remote.MenuApi

class MenuViewModel(private val storeId: String?) : ViewModel() {
    private val menuApi = MenuApi()
    val menus = MutableLiveData<ArrayList<Menu>>()
    val status = MutableLiveData<Int>()

    fun getMenu(lastId : String) {
        if (storeId != null) {
            menuApi.getMenu(menus, storeId, lastId)
        }
    }

}