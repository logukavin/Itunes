package com.sample.itunes.ui.base

import androidx.lifecycle.ViewModel
import com.sample.itunes.networkhelper.NetworkHelper
import com.sample.itunes.repository.AppRepository
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var appRepo: AppRepository



}