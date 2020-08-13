package com.mihailchistousov.unsplashv20.database

import androidx.room.*

@Dao
interface PhotoDao {
    @Query("SELECT * FROM unsplash_fav_photos")
    suspend fun getAllPhotos(): List<PhotoDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: PhotoDBEntity)

    @Delete
    suspend fun deletePhoto(photo: PhotoDBEntity)
}