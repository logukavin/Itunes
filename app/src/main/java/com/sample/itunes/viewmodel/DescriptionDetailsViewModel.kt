package com.sample.itunes.viewmodel

import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DescriptionDetailsViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider, private val networkHelper: NetworkHelper
) : BaseViewModel() {
}