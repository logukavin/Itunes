package com.sample.itunes.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.sample.itunes.R
import com.sample.itunes.databinding.ActivitySearchBinding
import com.sample.itunes.remote.AppConstants
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.ui.media.MediaActivity
import com.sample.itunes.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySearchBinding =
        ActivitySearchBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.searchButton.setOnClickListener {
            if (binding.etSearch.text?.isEmpty()!!) {
                showToast(getString(R.string.edt_name))
                return@setOnClickListener
            }
            var intent = Intent(applicationContext, MediaActivity::class.java)
            with(intent) {
                intent.putExtra(AppConstants.SEARCH_NAME, binding.etSearch.text.toString())
                startActivity(intent)
            }
        }
    }
}