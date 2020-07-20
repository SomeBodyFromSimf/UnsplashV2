package com.mihailchistousov.unsplashv20.database

import com.mihailchistousov.unsplashv20.Utils.EntityMapper
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.network.PhotoWwwEntity
import javax.inject.Inject

class DBMapper
@Inject
constructor() : EntityMapper<PhotoDBEntity, Photo> {
    override fun mapFromEntity(entity: PhotoDBEntity?): Photo? {
        return entity?.let {
            Photo(
                entity.id,
                entity.description,
                Photo.PhotoUrl(
                    entity.urls?.full,
                    entity.urls?.small,
                    entity.urls?.thumb)
            )
        }
    }

    override fun mapToEntity(domainModel: Photo?): PhotoDBEntity? {
        return domainModel?.let {
            PhotoDBEntity(
                domainModel.id,
                domainModel.description,
                PhotoDBEntity.PhotoDBUrl(
                    domainModel.urls?.full,
                    domainModel.urls?.small,
                    domainModel.urls?.thumb)
            )
        }
    }

    fun mapFromEntityList(entities: List<PhotoDBEntity?>) : List<Photo?> =
        entities.map {mapFromEntity(it)}

}