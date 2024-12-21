package com.sample.itunes.ui.description

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.sample.itunes.databinding.ActivityDescriptionDetailsBinding
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.viewmodel.DescriptionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionDetailsActivity: BaseActivity<ActivityDescriptionDetailsBinding>() {
    private val descriptionDetailsViewModel: DescriptionDetailsViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDescriptionDetailsBinding =
        ActivityDescriptionDetailsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    }