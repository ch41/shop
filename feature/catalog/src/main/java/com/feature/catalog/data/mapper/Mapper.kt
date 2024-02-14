package com.feature.catalog.data.mapper

import com.core.network.models.Item
import com.core.network.models.ListOfProductsResponse
import com.feature.catalog.domain.models.CatalogFeedbackEntity
import com.feature.catalog.domain.models.CatalogInfoEntity
import com.feature.catalog.domain.models.CatalogItemEntity
import com.feature.catalog.domain.models.CatalogPriceEntity

fun Item.toCatalogItem() = CatalogItemEntity(
    id,
    title,
    subtitle,
    CatalogPriceEntity(
        this.price.price,
        this.price.discount,
        this.price.priceWithDiscount, this.price.unit
    ),
    CatalogFeedbackEntity(this.feedback.count, this.feedback.rating.toFloat()),
    tags,
    available,
    description,
    info.map { CatalogInfoEntity(it.title, it.value) },
    ingredients,
    isFavorite = false
)