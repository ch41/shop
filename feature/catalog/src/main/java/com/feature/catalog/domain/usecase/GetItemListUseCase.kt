package com.feature.catalog.domain.usecase

import com.feature.catalog.domain.CatalogRepository
import com.feature.catalog.domain.models.CatalogItemEntity

class GetItemListUseCase(
    private val catalogRepository: CatalogRepository
) {
    suspend operator fun invoke(): List<CatalogItemEntity> {
        return catalogRepository.getItemList()
    }
}