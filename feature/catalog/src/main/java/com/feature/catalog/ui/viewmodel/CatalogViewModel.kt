package com.feature.catalog.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.core.ui.base.BaseViewModel
import com.feature.catalog.domain.models.CatalogItemEntity
import com.feature.catalog.domain.models.TagModel
import com.feature.catalog.domain.usecase.GetItemListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogViewModel(
    val getItemListUseCase: GetItemListUseCase
) : BaseViewModel() {

    private var _itemsListState = MutableStateFlow(emptyList<CatalogItemEntity>())
    val itemList = _itemsListState
    init {
        viewModelScope.launch {
            _itemsListState.update { getItemListUseCase() }
        }
    }

    val tags = arrayListOf(
        TagModel(
            id = "1",
            name = "Смотреть все",
            type = "all",
            isClicked = true
        ),
        TagModel(
            id = "2",
            name = "Тело",
            type = "body"
        ),
        TagModel(
            id = "3",
            name = "Лицо",
            type = "face"
        ),
        TagModel(
            id = "4",
            name = "Маска",
            type = "mask"
        ),
        TagModel(
            id = "5",
            name = "Масло",
            type = "suntan"
        )
    )


}