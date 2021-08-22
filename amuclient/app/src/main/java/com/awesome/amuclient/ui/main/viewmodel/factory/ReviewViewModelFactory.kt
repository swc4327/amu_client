package com.awesome.amuclient.ui.main.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.amuclient.ui.main.viewmodel.ReviewViewModel

class ReviewViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            ReviewViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}