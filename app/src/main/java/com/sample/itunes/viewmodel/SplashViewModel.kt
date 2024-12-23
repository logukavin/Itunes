package com.sample.itunes.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.ui.base.BaseViewModel
import com.sample.itunes.utils.CommonUI
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
private val dispatcherProvider: DispatcherProvider,
private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)  // Set to true initially to show loading screen
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _isDeviceRooted = MutableStateFlow(false)
    val isDeviceRooted: StateFlow<Boolean> get() = _isDeviceRooted.asStateFlow()

    init {
        checkDeviceRootStatus()
    }

    private fun checkDeviceRootStatus() {
        viewModelScope.launch(dispatcherProvider.io) {
            val isRooted = CommonUI.isRooted()
            _isDeviceRooted.value = isRooted
            if (!isRooted){
                startLoadingProcess()
            }
        }
    }

    private fun startLoadingProcess() {
        viewModelScope.launch {
                delay(4000)
                _isLoading.value = true
        }
    }
}
