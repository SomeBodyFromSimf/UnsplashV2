package com.mihailchistousov.unsplashv20.utils

import com.mihailchistousov.unsplashv20.model.Photo

sealed class MainStateEvent {
    object GetPhotosEvent : MainStateEvent()
    data class AddToDBEvent(val photo: Photo) : MainStateEvent()
    data class RemoveFromDB(val photo: Photo) : MainStateEvent()
    object ClearPage : MainStateEvent()
    object AddPage : MainStateEvent()
}

enum class DbENum {
    ADD, REMOVE
}

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}