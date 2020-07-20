package com.mihailchistousov.unsplashv20.di

import android.content.Context
import androidx.room.Room
import com.mihailchistousov.unsplashv20.database.AppDataBase
import com.mihailchistousov.unsplashv20.database.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePhotoDao(appDataBase: AppDataBase): PhotoDao {
        return appDataBase.photoDao()
    }
}
