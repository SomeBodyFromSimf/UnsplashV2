package com.mihailchistousov.unsplashv20.repository

import com.mihailchistousov.unsplashv20.database.PhotoDao
import com.mihailchistousov.unsplashv20.database.mapToDBEntity
import com.mihailchistousov.unsplashv20.database.mapToPhotoList
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import com.mihailchistousov.unsplashv20.network.UnsplashAPI
import com.mihailchistousov.unsplashv20.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository
@Inject
constructor(
    private val photoDao: PhotoDao,
    private val api: UnsplashAPI
) {
    suspend fun getPhotos(page: Int): Flow<DataState<List<PhotoWithLike>>> = flow {
        try {
            emit(DataState.Loading)
            val photosFromWWW = api.getPhotos(page).filterNotNull()
            val photosFromDB = photoDao.getAllPhotos().mapToPhotoList()
            val resultList = mutableListOf<PhotoWithLike>().apply {
                photosFromWWW.forEach {
                    add(PhotoWithLike(it, photosFromDB.contains(it)))
                }
            }
            emit(DataState.Success(resultList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    suspend fun addToDB(photo: Photo) {
        photoDao.addPhoto(photo.mapToDBEntity())
    }

    suspend fun removeFromDB(photo: Photo) {
        photoDao.deletePhoto(photo.mapToDBEntity())
    }
}