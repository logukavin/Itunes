package com.sample.itunes.viewmodel

import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider, private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _selectedFragment = MutableStateFlow<String>("")
    val selectedFragment: StateFlow<String> = _selectedFragment

    fun setSelectedFragment(fragment: String) {
        _selectedFragment.value = fragment
    }
}