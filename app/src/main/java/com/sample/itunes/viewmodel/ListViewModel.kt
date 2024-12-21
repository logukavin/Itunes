package com.sample.itunes.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.model.AllResponse
import com.sample.itunes.model.ResultsItem
import com.sample.itunes.model.ResultsItems
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.ui.base.BaseViewModel
import com.sample.itunes.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val dispatcherProvider: DispatcherProvider, private val networkHelper: NetworkHelper) : BaseViewModel() {

    private val _allResponse = MutableStateFlow<UIState<AllResponse>>(UIState.Loading)
    val allResponse: StateFlow<UIState<AllResponse>> = _allResponse

    private val mediaTypes = listOf(
        "music", "movie", "podcast", "audiobook", "software",
        "ebook", "tvShow", "musicVideo", "shortFilm"
    )

    init {
        getSearchDetailsSequentially()
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
                    Log.e("ListViewModel", mediaType)
                    try {
                        val result = appRepo.getSearchListDeatils("The+Beatles", mediaType)
                            .flowOn(dispatcherProvider.io)
                            .catch { exception ->
                                // Log the error and continue to the next media type
                                Log.e("ListViewModel", "Error fetching data for $mediaType", exception)
                            }
                            .single()

                        // Ensure non-null results are added to the list
                        result.results?.filterNotNull()?.let {
                            allResults.addAll(it)
                        }
                    } catch (e: Exception) {
                        // Handle individual errors for each mediaType but continue with the rest of the types
                        Log.e("ListViewModel", "Error processing $mediaType", e)
                    }
                }

                // Emit success with collected results
                _allResponse.emit(UIState.Success(AllResponse(allResults.size, allResults)))

            } catch (e: Exception) {
                // If there's a failure in the entire process, emit failure
                _allResponse.emit(UIState.Failure(e))
            }
        }
    }
}
