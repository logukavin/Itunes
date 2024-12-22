package com.sample.itunes.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sample.itunes.R
import com.sample.itunes.databinding.FragmentGridBinding
import com.sample.itunes.model.ChildItem
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.ui.base.BaseFragment
import com.sample.itunes.ui.base.UIState
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.utils.CommonUI.showGone
import com.sample.itunes.utils.CommonUI.showVisible
import com.sample.itunes.viewmodel.GridLIstViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GridFragment : BaseFragment<FragmentGridBinding>() {
    private val listViewModel: GridLIstViewModel by viewModels()
    private lateinit var adapter: GridListAdapter
    private val expandedGroups = mutableSetOf<Int>()

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentGridBinding =
        FragmentGridBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.expandableListView.setOnGroupExpandListener { groupPosition ->
            expandedGroups.add(groupPosition)
        }

        binding.expandableListView.setOnGroupCollapseListener { groupPosition ->
            expandedGroups.remove(groupPosition)
        }

        lifecycleScope.launch {
            listViewModel.allResponse.collect { state ->
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
                        CommonUI.showToast(requireContext(), getString(errorText))
                    }

                    is UIState.Success -> {
                        binding.progress.showGone()
                        val uniqueChildItemList = mutableMapOf<String, MutableList<ChildItem>>()
                        val uniqueList = mutableListOf<String>()

                        for (i in state.data.results!!) {
                            val childItem = ChildItem(
                                i!!.collectionCensoredName.toString(),i.artistName.toString(),i.previewUrl.toString(),i.primaryGenreName.toString(),i.longDescription.toString(), i.artworkUrl100.toString()
                            )

                            val parentKind = i.kind.takeIf { !it.isNullOrEmpty() } ?: i.wrapperType
                            if (!uniqueList.contains(parentKind)) {
                                uniqueList.add(parentKind!!)
                                val childItemList = mutableListOf(childItem)
                                uniqueChildItemList[parentKind] = childItemList
                            } else {
                                uniqueChildItemList[parentKind]?.add(childItem)
                            }
                        }
                        adapter = GridListAdapter(requireContext(), uniqueList, uniqueChildItemList)
                        binding.expandableListView.setAdapter(adapter)

                        for (i in 0 until adapter.groupCount) {
                            binding.expandableListView.expandGroup(i)
                        }

                        expandedGroups.forEach { groupPosition ->
                            binding.expandableListView.expandGroup(groupPosition)
                        }
                    }

                    else -> {
                        binding.progress.showGone()
                        // Handle other states if necessary
                    }
                }
            }
        }
    }
}
