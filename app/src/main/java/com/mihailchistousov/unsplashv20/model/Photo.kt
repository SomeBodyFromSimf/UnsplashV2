package com.mihailchistousov.unsplashv20.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Photo(
    var id: String,
    var description: String?,
    var urls: PhotoUrl
) : Parcelable {
    @Parcelize
    data class PhotoUrl(
        var full: String,
        var small: String,
        var thumb: String
    ) : Parcelable
}