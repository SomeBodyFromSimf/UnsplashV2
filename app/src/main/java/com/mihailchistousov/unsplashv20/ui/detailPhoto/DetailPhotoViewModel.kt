package com.mihailchistousov.unsplashv20.ui.detailPhoto

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.repository.Repository
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

    fun setStateEvent(mainStateEvent: MainStateEven) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEven.AddToDBEven -> {
                    val photo = mainStateEvent.photo
                    mainRepository.addToDB(photo)
                    _dbChange.value = photo.id to DbENum.ADD
                }
                is MainStateEven.RemoveFromD -> {
                    val photo = mainStateEvent.photo
                    mainRepository.removeFromDB(photo)
                    _dbChange.value = photo.id to DbENum.REMOVE
                }
            }
        }
    }

    companion object {
        enum class DbENum {
            ADD, REMOVE
        }
    }
}

sealed class MainStateEven {
    data class GetPhotosEven(val id: Int) : MainStateEven()
    data class AddToDBEven(val photo: Photo) : MainStateEven()
    data class RemoveFromD(val photo: Photo) : MainStateEven()
}