package com.awesome.amuclient.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReserveViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReserveViewModel::class.java)) {
            ReserveViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}