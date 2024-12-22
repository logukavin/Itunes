package com.sample.itunes.ui.media

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.sample.itunes.R
import com.sample.itunes.application.AppController
import com.sample.itunes.databinding.ActivityMediaBinding
import com.sample.itunes.model.ChildItem
import com.sample.itunes.model.MediaOption
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.ui.base.UIState
import com.sample.itunes.ui.dashboard.DashBoardActivity
import com.sample.itunes.ui.description.DescriptionDetailsActivity
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.utils.CommonUI.showGone
import com.sample.itunes.utils.CommonUI.showVisible
import com.sample.itunes.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaActivity : BaseActivity<ActivityMediaBinding>() {

    private val mediaViewModel: MediaViewModel by viewModels()
    private val mediaAdapter = MediaAdapter()
    private val mediaType = mutableListOf<String>() // Using a Set instead of a List to avoid duplicates
    private var searchName:String?=null
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMediaBinding =
        ActivityMediaBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvMedia.adapter = mediaAdapter
         searchName = intent.getStringExtra(AppConstants.SEARCH_NAME)
        lifecycleScope.launch {
            mediaViewModel.updateSearchName(searchName.toString())
        }

        lifecycleScope.launch {
            mediaViewModel.searchResponse.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        binding.progress.showVisible()
                    }

                    is UIState.Failure -> {
                        binding.progress.showGone()
                        val errorText = when (state.throwable) {
                            is NoInternetException -> R.string.no_internet_available
                            else -> R.string.something_went_wrong_try_again
                        }
                        CommonUI.showToast(this@MediaActivity, getString(errorText))
                    }

                    is UIState.Success -> {
                        binding.progress.showGone()
                        val mediaList = state.data.results?.mapNotNull {
                            val kind = it!!.kind.takeIf { it.isNullOrEmpty().not() } ?: it.wrapperType
                            kind?.let { MediaOption(it) }
                        }.orEmpty()

                        mediaAdapter.updateMediaAdapter(mediaList.distinct())
                    }

                    else -> {
                        binding.progress.showGone()
                    }
                }
            }
        }

        binding.tvSave.setOnClickListener {
            val checkedItems = mediaAdapter.getCheckedItems()
            checkedItems.forEach { item ->
                mediaType.add(item.name)
            }
            lifecycleScope.launch {
                Log.v("asdfghjk",mediaType.size.toString())
                appPreference.saveListToDataStore(mediaType)
            }

            val intent = Intent(AppController.appContext, DashBoardActivity::class.java)
            /*.apply {
                intent.putExtra(AppConstants.COLLECTION_NAME,"")
                intent.putExtra(AppConstants.ARTIST_NAME,"")
                intent.putExtra(AppConstants.PREVIEW_URL,"")
                intent.putExtra(AppConstants.PRIMARY_GENRE_NAME,"")
                intent.putExtra(AppConstants.LONG_DESCRIPTION,"")
                intent.putExtra(AppConstants.ART_WORK_URL,"")
            }*/
            startActivity(intent)

        }
    }
}