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
                   var is_reviewed: String,
                   var is_confirmed: String,
                   var is_completed: String) : Parcelable