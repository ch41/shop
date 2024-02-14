package com.core.network.models


import com.google.gson.annotations.SerializedName

data class ListOfProductsResponse(
    @SerializedName("items")
    val items: List<Item>
)

data class Feedback(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rating")
    val rating: Double
)

data class Info(
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: String
)

data class Price(
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("priceWithDiscount")
    val priceWithDiscount: String,
    @SerializedName("unit")
    val unit: String
)

data class Item(
    @SerializedName("available")
    val available: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("feedback")
    val feedback: Feedback,
    @SerializedName("id")
    val id: String,
    @SerializedName("info")
    val info: List<Info>,
    @SerializedName("ingredients")
    val ingredients: String,
    @SerializedName("price")
    val price: Price,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("title")
    val title: String
)