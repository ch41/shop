package com.feature.catalog.domain

import com.feature.catalog.domain.models.CatalogItemEntity

interface CatalogRepository {

    suspend fun getItemList() : List<CatalogItemEntity>
}