package com.example.androidtask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtask.base.BaseViewModel
import com.example.androidtask.data.local.entity.ImagesEntity
import com.example.androidtask.data.remote.DataState
import com.example.androidtask.data.usecase.FetchImagesUseCase
import com.example.androidtask.data.usecase.GetImagesFromLocalDB
import com.example.androidtask.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val fetchImagesUseCase: FetchImagesUseCase,
    private val getImagesFromLocalDB: GetImagesFromLocalDB
) : BaseViewModel() {

    private var _imagesList = MutableLiveData<List<ImagesEntity>>()
    var imagesLiveData: LiveData<List<ImagesEntity>> = _imagesList

    private var page = 1

    init {
        fetchImagesFromServer(page)
        fetchImagesFromLocalDB()
    }


    fun fetchImagesFromServer(
        page: Int = Constants.FIRST_PAGE,
        keyword: String = Constants.DEFAULT_KEYWORD_FRUIT
    ) {
        showLoader()
        viewModelScope.launch(Dispatchers.IO) {
            fetchImagesUseCase.invoke(page = page, keyword = keyword) {
                hideLoading()
                onResponseComplete(it)
            }
        }
    }

    private fun fetchImagesFromLocalDB() {
        showLoader()
        viewModelScope.launch(Dispatchers.IO) {
            getImagesFromLocalDB.invoke()
                .collectLatest { dataState ->
                    withContext(Dispatchers.Main) {
                        dataState?.let {
                            hideLoading()
                            _imagesList.postValue(dataState)
                        }
                    }
                }

        }
    }
}
