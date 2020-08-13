package com.mihailchistousov.unsplashv20.network

import android.os.Parcelable
import com.mihailchistousov.unsplashv20.model.Photo
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PhotoWWWEntity(
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

fun PhotoWWWEntity.mapToPhoto(): Photo {
    return Photo(
        id = id,
        description = description,
        urls = Photo.PhotoUrl(
            full = urls.full,
            small = urls.small,
            thumb = urls.thumb
        )
    )
}

fun List<PhotoWWWEntity?>.mapToPhotoList(): List<Photo> {
    return filterNotNull().map { it.mapToPhoto() }
}