package com.mihailchistousov.unsplashv20.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoWithLike(
    val photo: Photo,
    var like: Boolean
) : Parcelable