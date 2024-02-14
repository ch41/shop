package com.feature.catalog.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatalogItemEntity(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: CatalogPriceEntity,
    val feedback: CatalogFeedbackEntity,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<CatalogInfoEntity>,
    val ingredients: String,
    val isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class CatalogPriceEntity(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable

@Parcelize
data class CatalogFeedbackEntity(
    val count: Int,
    val rating: Float
) : Parcelable

@Parcelize
data class CatalogInfoEntity(
    val title: String,
    val value: String
) : Parcelable

data class ProductImageModel(
    val id: String,
    val image: List<Int>
)