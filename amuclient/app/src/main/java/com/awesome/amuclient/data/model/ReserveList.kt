package com.awesome.amuclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReserveList(val store: Store, val reserve: Reserve) : Parcelable
