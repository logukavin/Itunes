package com.sample.itunes.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sample.itunes.databinding.ActivityMediaBinding
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaActivity: BaseActivity<ActivityMediaBinding>() {
    private val mediaViewModel: MediaViewModel by viewModels()
    private val mediaAdapter = MediaAdapter()


    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMediaBinding =
        ActivityMediaBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvMedia.adapter = mediaAdapter

        lifecycleScope.launch {
            mediaViewModel.mediaOption.collect { mediaOptions ->
                mediaAdapter.updateMediaAdapter(mediaOptions)
            }
        }

    }
}