package com.awesome.amuclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(var uid: String,
                  var nickname: String,
                  var image: String,
                  var count: String,
                  var point: String) : Parcelable