package com.sample.itunes.viewmodel

import androidx.lifecycle.viewModelScope
import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider, private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(4000)
            _isLoading.value = false
        }
    }
}