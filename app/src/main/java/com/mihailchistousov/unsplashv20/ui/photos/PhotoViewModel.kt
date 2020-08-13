package com.mihailchistousov.unsplashv20.ui.photos

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mihailchistousov.unsplashv20.model.PhotoWithLike
import com.mihailchistousov.unsplashv20.repository.Repository
import com.mihailchistousov.unsplashv20.utils.DataState
import com.mihailchistousov.unsplashv20.utils.DbENum
import com.mihailchistousov.unsplashv20.utils.MainStateEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PhotoViewModel
@ViewModelInject
constructor(
    private val mainRepository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var list = mutableListOf<PhotoWithLike>()
    private val mutablePhoto = MutableLiveData<DataState<List<PhotoWithLike>>>()
    val photos: LiveData<DataState<List<PhotoWithLike>>>
        get() = mutablePhoto

    private val _dbChange: MutableLiveData<Pair<String, DbENum>> = MutableLiveData()
    val dbChange: LiveData<Pair<String, DbENum>>
        get() = _dbChange

    private var pages = 1

    init {
        setStateEvent(MainStateEvent.GetPhotosEvent)
    }

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetPhotosEvent -> {
                    mainRepository.getPhotos(pages)
                        .onEach { dataState ->
                            subscribeDataState(dataState)
                        }.launchIn(viewModelScope)
                }
                is MainStateEvent.ClearPage -> {
                    pages = 1
                    setStateEvent(MainStateEvent.GetPhotosEvent)
                }
                is MainStateEvent.AddPage -> {
                    pages++
                    setStateEvent(MainStateEvent.GetPhotosEvent)
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

    private fun subscribeDataState(dataState: DataState<List<PhotoWithLike>>) {
        when (dataState) {
            is DataState.Loading -> {
                mutablePhoto.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mutablePhoto.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                if (pages == 1)
                    list.clear()
                list.addAll(dataState.data)
                mutablePhoto.postValue(DataState.Success(list))
            }
        }
    }
}

