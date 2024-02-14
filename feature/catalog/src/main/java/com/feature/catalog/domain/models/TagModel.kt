package com.feature.catalog.domain.models

data class TagModel(
    val id: String,
    val name: String,
    val type: String,
    val isClicked: Boolean = false
)
