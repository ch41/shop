package com.feature.catalog.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.core.common.delegates.uiLazy
import com.core.ui.base.BaseFragment
import com.feature.catalog.databinding.FragmentCatalogBinding
import com.feature.catalog.ui.adapter.ProductsAdapter
import com.feature.catalog.ui.adapter.TagsAdapter
import com.feature.catalog.ui.viewmodel.CatalogViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CatalogFragment : BaseFragment<FragmentCatalogBinding>() {

    private val viewModel by viewModel<CatalogViewModel>()
    private var currentTag = "all"
    private val tagAdapter by uiLazy {
        TagsAdapter(
            onTagClick = { tagModel ->
                currentTag = tagModel.type

                changeClickedStateOnTag(tagModel.id)
//                viewModel.perform(CatalogViewEvent.SortByTag(tagModel.type))
            },
            cancelSort = {
                currentTag = "all"
                changeClickedStateOnTag("")
//                viewModel.perform(CatalogViewEvent.SortByTag("all"))
            }
        )
    }

    private val productsAdapter by uiLazy {
        ProductsAdapter(
            onItemClick = { model ->
//                viewModel.perform(CatalogViewEvent.NavigateToDetails(model))
            },
            addToFavorite = { isAdd, model ->
//                viewModel.perform(CatalogViewEvent.AddToFavorites(isAdd, model, currentTag))
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tagsRecyclerView.adapter = tagAdapter
        tagAdapter.submitList(viewModel.tags)
        binding.productsRecyclerView.itemAnimator = null
        binding.productsRecyclerView.adapter = productsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.itemList.collect {
                Log.d("itemList", "onViewCreated: $it ")
                productsAdapter.submitList(it)
            }
        }
    }

    private fun changeClickedStateOnTag(clickedTagId: String) {

        val tagsWithClickedList = viewModel.tags.map {
            if (it.id == clickedTagId) {
                it.copy(isClicked = true)
            } else {
                it.copy(isClicked = false)
            }
        }

        tagAdapter.submitList(tagsWithClickedList)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCatalogBinding.inflate(inflater, container, false)

}