package com.sample.itunes.ui.grid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sample.itunes.R
import com.sample.itunes.databinding.FragmentGridBinding
import com.sample.itunes.model.ChildItem
import com.sample.itunes.model.ParentItem
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.ui.base.BaseFragment
import com.sample.itunes.ui.base.UIState
import com.sample.itunes.ui.list.ListItemAdapter
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.viewmodel.GridViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GridFragment : BaseFragment<FragmentGridBinding>() {
    private val listViewModel: GridViewModel by viewModels()
    private lateinit var adapter: GridItemAdapter
    override fun inflateViewBinding(inflater: LayoutInflater): FragmentGridBinding =
        FragmentGridBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            listViewModel.allResponse.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        // Show loading UI (e.g., progress bar)
                    }

                    is UIState.Failure -> {
                        val errorText = when (state.throwable) {
                            is NoInternetException -> R.string.no_internet_available
                            else -> R.string.something_went_wrong_try_again
                        }
                        CommonUI.showToast(requireContext(), getString(errorText))
                    }

                    is UIState.Success -> {
                        val uniqueChildItemList = mutableListOf<ChildItem>()
                        val uniqueList = mutableListOf<ParentItem>()

                        for (i in state.data.results!!) {
                            // Create ChildItem for each result
                            val childItem = ChildItem(
                                i!!.collectionCensoredName.toString(),
                                i.artworkUrl100.toString()
                            )

                            if (!uniqueChildItemList.contains(childItem)) {
                                uniqueChildItemList.add(childItem)
                            }

                            val parentItem = ParentItem(
                                i.kind.toString(),
                                true, // Assuming this flag is required for each ParentItem
                                uniqueChildItemList
                            )
                            if (!uniqueList.any { it.kind == parentItem.kind }) {
                                uniqueList.add(parentItem)
                            }
                        }
                        Log.d("uniqueList",uniqueList.size.toString())

                        adapter = GridItemAdapter(uniqueList)
                        binding.rvGrid.adapter = adapter
                    }

                    else -> {
                        // Handle other states if necessary
                    }
                }
            }
        }

    }
}

//        musicGridPagerAdapter.addLoadStateListener { loadState ->
//            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) binding.progress.isVisible =
//                true
//            else {
//                binding.progress.isVisible = false
//                val errorState = when {
//                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
//                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
//                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
//                    else -> null
//                }
//                errorState?.let {}
//
//            }
//        }


//        lifecycleScope.launch {
//            layoutViewModel.iTunesResponse.collectLatest { state ->
//                when (state) {
//                    is UIState.Loading -> {
//                        // Optionally show a loading indicator
//                    }
//
//                    is UIState.Failure -> {
//                        val errorText = when (state.throwable) {
//                            is NoInternetException -> R.string.no_internet_available
//                            else -> R.string.something_went_wrong_try_again
//                        }
//                        CommonUI.showToast(requireContext(), getString(errorText))
//                    }
//
//                    is UIState.PagingSuccess -> {
//                        val pagingData = state.pagingData
//                        pagingData.let { musicGridPagerAdapter.submitData(lifecycle, it) }
//                    }
//
//                    else -> {
//                        // Handle other states if necessary
//                    }
//                }
//            }
//        }
//    }
//}