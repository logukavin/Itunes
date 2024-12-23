package com.sample.itunes.ui.dashboard

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sample.itunes.R
import com.sample.itunes.databinding.ActivityDashboardBinding
import com.sample.itunes.ui.base.BaseActivity
import com.sample.itunes.ui.grid.GridFragment
import com.sample.itunes.ui.list.ListFragment
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.viewmodel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.security.MessageDigest
import java.security.PublicKey
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val dashBoardViewModel: DashBoardViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            commonLayout.tvTitle.text = getString(R.string.itunes)
            commonLayout.imgBack.setOnClickListener { finish() }
            tvGrid.setOnClickListener { onTextClicked(it, getString(R.string.grid_layout)) }
            tvList.setOnClickListener { onTextClicked(it, getString(R.string.list_layout)) }

            dashBoardViewModel.setSelectedFragment(getString(R.string.grid_layout))

            lifecycleScope.launch {
                dashBoardViewModel.selectedFragment.collect { fragmentName ->
                    when (fragmentName) {
                        getString(R.string.grid_layout) -> loadFragment(GridFragment())
                        getString(R.string.list_layout) -> loadFragment(ListFragment())
                    }
                }
            }
        }

        // Fetch public key hash (without GlobalScope)
        getPublicKeyHash()
    }

    private fun onTextClicked(view: View, fragment: String) {
        resetText()
        val selectedButton = view as TextView
        selectedButton.background = ContextCompat.getDrawable(this, R.drawable.bg_selected)
        dashBoardViewModel.setSelectedFragment(fragment)
    }

    private fun resetText() {
        binding.tvGrid.background = ContextCompat.getDrawable(this, R.drawable.bg_unselected)
        binding.tvList.background = ContextCompat.getDrawable(this, R.drawable.bg_unselected)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun getPublicKeyHash() {
        lifecycleScope.launch {
            val url = "https://itunes.apple.com"
            val publicKeyHash = CommonUI.generatePublicKeyHashFromServer(url)
            if (publicKeyHash != null) {
                println("Public Key Hash: $publicKeyHash")
            } else {
                println("Failed to generate public key hash")
            }
        }
    }
}
