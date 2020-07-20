package com.mihailchistousov.unsplashv20.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mihailchistousov.unsplashv20.model.Photo

@Database(
    entities = [PhotoDBEntity::class],
    version = 1,
    exportSchema = false
 )
abstract class AppDataBase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        const val DATABASE_NAME: String = "unsplash_db"
    }
}