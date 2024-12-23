package com.sample.itunes.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieDrawable
import com.sample.itunes.R
import com.sample.itunes.databinding.ActivitySplashBinding
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.ui.dashboard.DashBoardActivity
import com.sample.itunes.ui.search.SearchActivity
import com.sample.itunes.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding =
        ActivitySplashBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lottieAnimationView.apply {
            playAnimation()
            setAnimation(R.raw.animation)
            repeatCount = LottieDrawable.INFINITE
            speed = 1.5f
        }

        lifecycleScope.launch {
            launch { findDeviceRooted() }
            launch { findLoading() }
        }
    }

    private suspend fun findLoading() {
        splashViewModel.isLoading.collect { isLoading ->
            if (isLoading) {
                val intent = Intent(this@SplashActivity, SearchActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private suspend fun findDeviceRooted() {
        splashViewModel.isDeviceRooted.collect { isRooted ->
            withContext(Dispatchers.Main) {
                val message = if (isRooted) {
                    getString(R.string.your_device_is_rooted).also {
                        finish()
                    }
                } else {
                    getString(R.string.device_is_not_rooted)
                }
                showToast(message)
            }
        }
    }

}
