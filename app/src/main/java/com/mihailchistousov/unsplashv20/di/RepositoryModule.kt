package com.mihailchistousov.unsplashv20.di

import com.mihailchistousov.unsplashv20.database.DBMapper
import com.mihailchistousov.unsplashv20.database.PhotoDao
import com.mihailchistousov.unsplashv20.network.NetworkMapper
import com.mihailchistousov.unsplashv20.network.UnsplashAPI
import com.mihailchistousov.unsplashv20.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        photoDao: PhotoDao,
        api: UnsplashAPI,
        roomMapper: DBMapper,
        networkMapper: NetworkMapper
    ): Repository{
        return Repository(photoDao,api,roomMapper,networkMapper)
    }

}