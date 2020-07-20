package com.mihailchistousov.unsplashv20.network

import com.mihailchistousov.unsplashv20.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {
    @GET("photos/?per_page=25&$CLIENT_ID")
    suspend fun getPhotos(
        @Query("page") page: Int
    ): List<PhotoWwwEntity?>

    @GET("photos/{id}?$CLIENT_ID")
    suspend fun getCurrentPhotos(
        @Path("id") id: String
    ): PhotoWwwEntity?

    companion object {
        const val CLIENT_ID = "client_id=a9911b96cdcb6fe46785e02a61c5e37e88043e035d65929a8fe4849aeede9c66"
    }
}