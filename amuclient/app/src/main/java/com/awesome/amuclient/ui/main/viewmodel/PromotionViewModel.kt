package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.amuclient.data.model.Promotion
import com.awesome.amuclient.data.model.PromotionList
import com.awesome.amuclient.data.model.remote.PromotionApi

class PromotionViewModel() : ViewModel() {
    private val promotionApi = PromotionApi()
    val promotionLists = MutableLiveData<ArrayList<PromotionList>>()

    fun getPromotions(clientId : String) {
        promotionApi.getStoreForPromotion(clientId, promotionLists)
    }
}