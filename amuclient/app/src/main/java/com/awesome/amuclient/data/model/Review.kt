package com.awesome.amuclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Review(var id: Int?,
                  var review_image: String,
                  var comment: String,
                  var time: String,
                  var store_id: String,
                  var client_id: String,
                  var point: String,
                  var reserve_id: String?) : Parcelable