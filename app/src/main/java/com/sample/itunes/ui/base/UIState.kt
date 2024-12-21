package com.sample.itunes.ui.base

import androidx.paging.PagingData
import com.sample.itunes.model.ResultsItem

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Failure(val throwable: Throwable) : UIState<Nothing>()
    data class PagingSuccess<out T>(val pagingData: PagingData<ResultsItem>) : UIState<T>()
}
