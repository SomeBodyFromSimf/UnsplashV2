package com.mihailchistousov.unsplashv20.ui.photos

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mihailchistousov.unsplashv20.Utils.DataState
import com.mihailchistousov.unsplashv20.model.Photo
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import com.mihailchistousov.unsplashv20.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PhotoViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _statusState: MutableLiveData<DataState<*>> = MutableLiveData()
    val _status: LiveData<DataState<*>>
        get() = _statusState

    private val _photos: MutableLiveData<List<PhotoWithLike>> = MutableLiveData()
    val _photosBase: LiveData<List<PhotoWithLike>>
        get() = _photos

    private val _dbChange: MutableLiveData<Pair<String, DbENum>> = MutableLiveData()
    val dbChange: LiveData<Pair<String, DbENum>>
        get() = _dbChange

    private val _pages: MutableLiveData<Int> = MutableLiveData(1)
    val pagesOnScreen: LiveData<Int>
        get() = _pages

    fun setPagesOnScreen(value: Int?) {
        _pages.postValue(value)
    }

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetPhotosEvent -> {
                    mainRepository.getPhotos(mainStateEvent.id)
                        .onEach { dataState ->
                            when (dataState) {
                                is DataState.Loading -> {
                                    _statusState.postValue(DataState.Loading)
                                }
                                is DataState.Error -> {
                                    _statusState.postValue(DataState.Error(dataState.exception))
                                }
                                is DataState.Success -> {
                                    _statusState.postValue(DataState.Success<Int?>(null))
                                    if(mainStateEvent.id != 1)
                                        _photos.postValue(_photos.value?.plus(dataState.data))
                                    else
                                        _photos.postValue(dataState.data)
                                }
                            }

                        }
                        .launchIn(viewModelScope)
                }
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


    companion object {
        enum class DbENum {
            ADD, REMOVE
        }
    }
}



sealed class MainStateEvent {
    data class GetPhotosEvent(val id: Int) : MainStateEvent()
    data class AddToDBEvent(val photo: Photo) : MainStateEvent()
    data class RemoveFromDB(val photo: Photo) : MainStateEvent()
}