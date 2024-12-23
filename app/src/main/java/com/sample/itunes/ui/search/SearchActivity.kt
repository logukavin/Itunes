package com.sample.itunes.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.sample.itunes.R
import com.sample.itunes.databinding.ActivitySearchBinding
import com.sample.itunes.model.MediaOption
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.ui.base.UIState
import com.sample.itunes.ui.media.MediaActivity
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.utils.CommonUI.showGone
import com.sample.itunes.utils.CommonUI.showVisible
import com.sample.itunes.viewmodel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(), TextWatcher {
    private val mediaViewModel: MediaViewModel by viewModels()
    private val searchAdapter = SearchAdapter()
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySearchBinding =
        ActivitySearchBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.etSearch.addTextChangedListener(this)
        binding.resultsRecyclerView.adapter = searchAdapter

        lifecycleScope.launch {
            mediaViewModel.updateSearchName("a")
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
                        CommonUI.showToast(this@SearchActivity, getString(errorText))
                    }

                    is UIState.Success -> {
                        binding.progress.showGone()
                        val mediaList = state.data.results?.mapNotNull {
                            val kind = it!!.kind.takeIf { it.isNullOrEmpty().not() } ?: it.wrapperType
                            kind?.let { MediaOption(it) }
                        }.orEmpty()

                        searchAdapter.updateSearchAdapter(mediaList.distinct())
                    }

                    else -> {
                        binding.progress.showGone()
                    }
                }
            }
        }


        binding.searchButton.setOnClickListener {
            if (binding.etSearch.text?.isEmpty()!!) {
                showToast(getString(R.string.edt_name))
                return@setOnClickListener
            }
            lifecycleScope.launch {
                appPreference.setTerm(binding.etSearch.text.toString())
            }

            val intent = Intent(applicationContext, MediaActivity::class.java)
                intent.putExtra(AppConstants.SEARCH_NAME, binding.etSearch.text.toString())
                startActivity(intent)
            }
        }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()) {
            lifecycleScope.launch {
                mediaViewModel.updateSearchName(s.toString())
            }
        }
    }
}