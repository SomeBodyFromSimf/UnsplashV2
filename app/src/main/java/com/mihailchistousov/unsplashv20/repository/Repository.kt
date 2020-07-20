package com.mihailchistousov.unsplashv20.repository

import com.mihailchistousov.unsplashv20.Utils.DataState
import com.mihailchistousov.unsplashv20.database.DBMapper
import com.mihailchistousov.unsplashv20.database.PhotoDao
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import com.mihailchistousov.unsplashv20.network.NetworkMapper
import com.mihailchistousov.unsplashv20.network.UnsplashAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository
constructor(
    private val photoDao: PhotoDao,
    private val api: UnsplashAPI,
    private val roomMapper: DBMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getPhotos(page : Int): Flow<DataState<List<PhotoWithLike>>> = flow {
        emit(DataState.Loading)
        try {
            val photosFromWWW = networkMapper.mapFromEntityList(api.getPhotos(page))
            val photosFromDB = roomMapper.mapFromEntityList(photoDao.getAllPhotos())
            val resultList = mutableListOf<PhotoWithLike>().apply {
                for(photo in photosFromWWW) {
                    photo?.let{
                        add(PhotoWithLike(photo,photosFromDB.contains(photo)))
                    }
                }
            }

            emit(DataState.Success(resultList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    suspend fun addToDB(photo: Photo){
        photoDao.addPhoto(roomMapper.mapToEntity(photo))
    }

    suspend fun removeFromDB(photo: Photo){
        photoDao.deletePhoto(roomMapper.mapToEntity(photo))
    }
}