package com.feature.catalog.data.repository

import android.util.Log
import com.core.network.services.ShopService
import com.feature.catalog.data.mapper.toCatalogItem
import com.feature.catalog.domain.CatalogRepository
import com.feature.catalog.domain.models.CatalogItemEntity

class CatalogRepositoryImpl(private val service: ShopService) : CatalogRepository {
    override suspend fun getItemList(): List<CatalogItemEntity> {
       return service.getListOfProducts().items.map {
           it.toCatalogItem()
       }!!
    }
}