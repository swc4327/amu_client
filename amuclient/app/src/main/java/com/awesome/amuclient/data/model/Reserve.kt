package com.awesome.amuclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Reserve(var id: Int?,
                   var name: String,
                   var phone: String,
                   var arrive: String,
                   var request: String,
                   var lat: String?,
                   var lng: String?,
                   var date : String?,
                   var store_id : String,
                   var client_id : String,
                   var isReviewed: String,
                   var isConfirmed: String,
                   var isCompleted: String) : Parcelable