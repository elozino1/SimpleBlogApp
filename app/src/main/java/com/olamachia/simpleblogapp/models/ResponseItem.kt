package com.olamachia.simpleblogapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable