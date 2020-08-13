package com.mihailchistousov.unsplashv20.ui.detailPhoto

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mihailchistousov.unsplashv20.repository.Repository
import com.mihailchistousov.unsplashv20.utils.DbENum
import com.mihailchistousov.unsplashv20.utils.MainStateEvent
import kotlinx.coroutines.launch

class DetailPhotoViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dbChange: MutableLiveData<Pair<String, DbENum>> = MutableLiveData()
    val dbChange: LiveData<Pair<String, DbENum>>
        get() = _dbChange

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.AddToDBEvent -> {
                    val photo = mainStateEvent.photo
                    mainRepository.addToDB(photo)
                    _dbChange.value = photo.id to DbENum.ADD
                }
                is MainStateEvent.RemoveFromDB -> {
                    val photo = mainStateEvent.photo
                    mainRepository.removeFromDB(photo)
                    _dbChange.value = photo.id to DbENum.REMOVE
                }
            }
        }
    }
}