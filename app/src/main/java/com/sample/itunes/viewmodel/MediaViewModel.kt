package com.sample.itunes.viewmodel

import androidx.lifecycle.viewModelScope
import com.sample.itunes.dispatcher.DispatcherProvider
import com.sample.itunes.model.MediaOption
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider, private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _mediaOption =
        MutableStateFlow<List<MediaOption>>(emptyList()) // Initial state is an empty list
    val mediaOption: StateFlow<List<MediaOption>> get() = _mediaOption

    init {
        viewModelScope.launch {
            val mediaOptions = listOf(
                MediaOption("Album"),
                MediaOption("Movieartist"),
                MediaOption("Ebook"),
                MediaOption("Movie"),
                MediaOption("Musicvideo"),
                MediaOption("Podcast"),
                MediaOption("Song")
            )
            _mediaOption.value = mediaOptions
        }

    }
}