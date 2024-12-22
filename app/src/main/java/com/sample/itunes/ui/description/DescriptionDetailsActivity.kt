package com.sample.itunes.ui.description

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.MediaController
import androidx.activity.viewModels
import com.sample.itunes.R
import com.sample.itunes.databinding.ActivityDescriptionDetailsBinding
import com.sample.itunes.extensions.loadUrl
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.utils.CommonUI.getFileExtension
import com.sample.itunes.utils.CommonUI.showGone
import com.sample.itunes.utils.CommonUI.showVisible
import com.sample.itunes.viewmodel.DescriptionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionDetailsActivity : BaseActivity<ActivityDescriptionDetailsBinding>() {

    private val descriptionDetailsViewModel: DescriptionDetailsViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDescriptionDetailsBinding =
        ActivityDescriptionDetailsBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.commonLayout.tvTitle.text = getString(R.string.media)
        binding.commonLayout.imgBack.setOnClickListener { finish() }

        val collectionName = intent.getStringExtra(AppConstants.COLLECTION_NAME)
        val artistName = intent.getStringExtra(AppConstants.ARTIST_NAME)
        val previewUrl = intent.getStringExtra(AppConstants.PREVIEW_URL)
        val primaryGenreName = intent.getStringExtra(AppConstants.PRIMARY_GENRE_NAME)
        val longDescription = intent.getStringExtra(AppConstants.LONG_DESCRIPTION)
        val artworkUrl = intent.getStringExtra(AppConstants.ART_WORK_URL)

        binding.imgMovie.loadUrl(artworkUrl)
        binding.tvName.text = collectionName
        binding.tvArtistName.text = artistName
        binding.tvAction.text = primaryGenreName
        binding.tvDesDetail.text = longDescription

        if (previewUrl.isNullOrEmpty()) {
            binding.videoView.showGone()
            binding.tvPreview.showGone()
        } else {
            binding.tvPreview.showVisible()
            binding.videoView.showVisible()
            val extension = getFileExtension(previewUrl)

            if (extension == "m4v" || extension == "mp4") {
                val uri = Uri.parse(previewUrl)
                binding.videoView.setVideoURI(uri)
                binding.videoView.start()

                val mediaController = MediaController(this)
                mediaController.setAnchorView(binding.videoView)
                binding.videoView.setMediaController(mediaController)
            } else {
                binding.videoView.showGone()
                binding.imgThumbnail.showVisible()
                binding.imgThumbnail.loadUrl(artworkUrl)
            }
        }
    }
}