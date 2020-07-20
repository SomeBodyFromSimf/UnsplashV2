package com.mihailchistousov.unsplashv20.ui.favorite

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mihailchistousov.unsplashv20.repository.Repository

class FavViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}