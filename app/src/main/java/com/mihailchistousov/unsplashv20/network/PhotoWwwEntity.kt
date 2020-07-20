package com.mihailchistousov.unsplashv20.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mihailchistousov.unsplashv20.model.Photo

data class PhotoWwwEntity(
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("description")
    @Expose
    var description: String?,
    @SerializedName("urls")
    @Expose
    var urls: PhotoWwwEntity.PhotoWwwUrlEntity?
) {
    data class PhotoWwwUrlEntity (
        @SerializedName("full")
        @Expose
        var full: String?,
        @SerializedName("small")
        @Expose
        var small: String?,
        @SerializedName("thumb")
        @Expose
        var thumb: String?
    )
}