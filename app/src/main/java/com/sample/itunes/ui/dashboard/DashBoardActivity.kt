package com.sample.itunes.ui.dashboard

import android.os.Bundle
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
import com.sample.itunes.viewmodel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<ActivityDashboardBinding>() {

    private val dashBoardViewModel: DashBoardViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.commonLayout.tvTitle.text=ContextCompat.getString(this,R.string.itunes)
        binding.tvGrid.setOnClickListener { onTextClicked(it, getString(R.string.grid_layout)) }
        binding.tvList.setOnClickListener { onTextClicked(it, getString(R.string.list_layout)) }

        lifecycleScope.launch {
            dashBoardViewModel.selectedFragment.collect { fragmentName ->
                when (fragmentName) {
                    getString(R.string.grid_layout) -> loadFragment(GridFragment())
                    getString(R.string.list_layout) -> loadFragment(ListFragment())
                }
            }
        }

        dashBoardViewModel.setSelectedFragment(getString(R.string.grid_layout))
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
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}