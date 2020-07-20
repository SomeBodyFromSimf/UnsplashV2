package com.mihailchistousov.unsplashv20.Utils


interface EntityMapper<Entity,DomainModel> {

    fun mapFromEntity(entity: Entity?) : DomainModel?
    fun mapToEntity(domainModel: DomainModel?) : Entity?

}