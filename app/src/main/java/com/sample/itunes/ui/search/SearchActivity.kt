package com.sample.itunes.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.sample.itunes.databinding.ActivitySearchBinding
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: BaseActivity<ActivitySearchBinding>() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySearchBinding =
        ActivitySearchBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}