package com.mihailchistousov.unsplashv20.network

import com.mihailchistousov.unsplashv20.Utils.EntityMapper
import com.mihailchistousov.unsplashv20.model.Photo
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<PhotoWwwEntity, Photo> {
    override fun mapFromEntity(entity: PhotoWwwEntity?): Photo? {
        return entity?.let {
            Photo(
                entity.id,
                entity.description,
                Photo.PhotoUrl(
                    entity.urls?.full,
                    entity.urls?.small,
                    entity.urls?.thumb
                )
            )
        }
    }

    override fun mapToEntity(domainModel: Photo?): PhotoWwwEntity? {
        return domainModel?.let {
            PhotoWwwEntity(
                domainModel.id,
                domainModel.description,
                PhotoWwwEntity.PhotoWwwUrlEntity(
                    domainModel.urls?.full,
                    domainModel.urls?.small,
                    domainModel.urls?.thumb
                )
            )
        }
    }

    fun mapFromEntityList(entities: List<PhotoWwwEntity?>): List<Photo?> =
        entities.map { mapFromEntity(it) }

}