package com.sample.itunes.viewmodel

import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.model.AllResponse
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.ui.base.BaseViewModel
import com.sample.itunes.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper) : BaseViewModel() {

    private val _searchName = MutableStateFlow("jack+johnson")

    private val _searchResponse = MutableStateFlow<UIState<AllResponse>>(UIState.Loading)
    val searchResponse: StateFlow<UIState<AllResponse>> = _searchResponse

    private suspend fun getSearchList(searchName: String) {
        if (!networkHelper.isNetworkConnected()) {
            _searchResponse.emit(UIState.Failure(throwable = NoInternetException()))
            return
        }
        _searchResponse.emit(UIState.Loading)

        appRepo.getSearchList(searchName)
            .flowOn(dispatcherProvider.io)
            .catch { exception ->
                _searchResponse.emit(UIState.Failure(exception))
            }
            .collect { response ->
                _searchResponse.emit(UIState.Success(response))
            }
    }

    suspend fun updateSearchName(newSearchName: String) {
        _searchName.value = newSearchName
        getSearchList(newSearchName)
    }
}
