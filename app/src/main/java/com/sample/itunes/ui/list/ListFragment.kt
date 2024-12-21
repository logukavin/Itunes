package com.sample.itunes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sample.itunes.R
import com.sample.itunes.databinding.FragmentListBinding
import com.sample.itunes.model.ChildItem
import com.sample.itunes.model.ParentItem
import com.sample.itunes.networkhelper.NoInternetException
import com.sample.itunes.ui.base.BaseFragment
import com.sample.itunes.ui.base.UIState
import com.sample.itunes.utils.CommonUI
import com.sample.itunes.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {
    private val listViewModel: ListViewModel by viewModels()
    private lateinit var adapter: ListItemAdapter

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentListBinding =
        FragmentListBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ListItemAdapter(emptyList())
        binding.rvList.adapter = adapter

        lifecycleScope.launch {
            listViewModel.allResponse.collect { state ->
                when (state) {
                    is UIState.Loading -> {
                        // Optionally show a loading indicator
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
                            val childItem = ChildItem(i!!.collectionCensoredName.toString(), i.artworkUrl100.toString())

                            if (!uniqueChildItemList.contains(childItem)) {
                                uniqueChildItemList.add(childItem)
                            }
                            val parentItem = ParentItem(
                                i.kind.toString(),
                                true,
                                uniqueChildItemList
                            )

                            if (!uniqueList.any { it.kind == parentItem.kind }) {
                                uniqueList.add(parentItem)
                            }
                        }

                        adapter = ListItemAdapter(uniqueList)
                        binding.rvList.adapter = adapter
                    }


                    else -> {
                        // Handle other states if necessary
                    }
                }
            }
        }

    }
}