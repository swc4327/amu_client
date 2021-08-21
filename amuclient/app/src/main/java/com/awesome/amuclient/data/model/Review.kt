package com.awesome.amuclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Review(var id: Int?,
                  var reviewImage: String,
                  var comment: String,
                  var time: String?,
                  var storeId: String,
                  var clientId: String,
                  var point: String,
                  var reserveId: String?) : Parcelable