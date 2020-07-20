package com.mihailchistousov.unsplashv20.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsplash_fav_photos")
data class PhotoDBEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @Embedded
    var urls: PhotoDBUrl?
) {
    data class PhotoDBUrl (
        @ColumnInfo(name = "full_link")
        var full: String?,

        @ColumnInfo(name = "small_link")
        var small: String?,

        @ColumnInfo(name = "thumb_link")
        var thumb: String?
    )
}