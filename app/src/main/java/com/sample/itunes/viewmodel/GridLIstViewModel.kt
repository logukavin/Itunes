package com.sample.itunes.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.itunes.application.AppController
import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.model.AllResponse
import com.sample.itunes.model.ResultsItems
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.preferences.AppPreference
import com.sample.itunes.ui.base.BaseViewModel
import com.sample.itunes.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridLIstViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : BaseViewModel() {

    private val _allResponse = MutableStateFlow<UIState<AllResponse>>(UIState.Loading)
    val allResponse: StateFlow<UIState<AllResponse>> = _allResponse
    private var mediaTypes = mutableListOf<String>()
    private var term:String?=null

    init {
        viewModelScope.launch {
            getLocalDataValues()
            delay(3000)
            getSearchDetailsSequentially()
        }

    }

    private suspend fun getLocalDataValues() {
        viewModelScope.launch {
            appPreference.getListFromDataStore().collect { dataList ->
                mediaTypes.addAll(dataList)
            }
        }

        viewModelScope.launch {
            appPreference.getTerm.collect{
                term=it
            }
        }

    }

    private fun getSearchDetailsSequentially() {
        viewModelScope.launch {
            if (!networkHelper.isNetworkConnected()) {
                _allResponse.emit(UIState.Failure(throwable = NoInternetException()))
                return@launch
            }

            _allResponse.emit(UIState.Loading)

            val allResults = mutableListOf<ResultsItems>()

            try {
                for (mediaType in mediaTypes) {
                    try {
                        val result = appRepo.getListDeatils(term?:"", mediaType)
                            .flowOn(dispatcherProvider.io)
                            .catch { exception ->
                                Log.e(
                                    "ListViewModel",
                                    "Error fetching data for $mediaType",
                                    exception
                                )
                            }
                            .single()
                        val uniqueResults =
                            result.results?.distinctBy { it!!.collectionCensoredName }!!
                                .toMutableList()

                        uniqueResults.filterNotNull().let {
                            allResults.addAll(it)
                        }
                    } catch (e: Exception) {
                        Log.e("ListViewModel", "Error processing $mediaType", e)
                    }
                }

                _allResponse.emit(UIState.Success(AllResponse(allResults.size, allResults)))

            } catch (e: Exception) {
                _allResponse.emit(UIState.Failure(e))
            }
        }
    }
}
