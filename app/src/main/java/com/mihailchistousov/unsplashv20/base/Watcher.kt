package com.mihailchistousov.unsplashv20.base

import com.mihailchistousov.unsplashv20.model.Photo


interface Watcher {
    fun showDetailedScreenPhoto(position: Int) {}
    fun addToDB(photo: Photo)
    fun removeFromDB(photo: Photo)
}