package com.feature.catalog.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.core.ui.databinding.ItemProductBinding
import com.feature.catalog.R
import com.feature.catalog.domain.models.CatalogItemEntity
import com.feature.catalog.domain.models.ProductImageModel


class ProductsAdapter(
    private val onItemClick: (model: CatalogItemEntity) -> Unit,
    private val addToFavorite: (isAdd: Boolean, model: CatalogItemEntity) -> Unit
): ListAdapter<CatalogItemEntity, ProductsAdapter.CatalogProductViewHolder>(DiffCallback()) {

    private val images = arrayListOf(
        ProductImageModel(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            image = listOf(com.core.ui.R.drawable.britva, com.core.ui.R.drawable.gel)
        ),
        ProductImageModel(
            id = "54a876a5-2205-48ba-9498-cfecff4baa6e",
            image = listOf(com.core.ui.R.drawable.penka, com.core.ui.R.drawable.lotion)
        ),
        ProductImageModel(
            id = "75c84407-52e1-4cce-a73a-ff2d3ac031b3",
            image = listOf(com.core.ui.R.drawable.gel, com.core.ui.R.drawable.britva)
        ),
        ProductImageModel(
            id = "16f88865-ae74-4b7c-9d85-b68334bb97db",
            image = listOf(com.core.ui.R.drawable.deco, com.core.ui.R.drawable.care)
        ),
        ProductImageModel(
            id = "26f88856-ae74-4b7c-9d85-b68334bb97db",
            image = listOf(com.core.ui.R.drawable.lotion, com.core.ui.R.drawable.deco)
        ),
        ProductImageModel(
            id = "15f88865-ae74-4b7c-9d81-b78334bb97db",
            image = listOf(com.core.ui.R.drawable.britva, com.core.ui.R.drawable.penka)
        ),
        ProductImageModel(
            id = "88f88865-ae74-4b7c-9d81-b78334bb97db",
            image = listOf(com.core.ui.R.drawable.care, com.core.ui.R.drawable.deco)
        ),
        ProductImageModel(
            id = "55f58865-ae74-4b7c-9d81-b78334bb97db",
            image = listOf(com.core.ui.R.drawable.penka, com.core.ui.R.drawable.gel)
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CatalogProductViewHolder(
        itemBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CatalogProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatalogProductViewHolder(val itemBinding: ItemProductBinding):
        RecyclerView.ViewHolder(itemBinding.root) {

        private val imagesAdapter = ImagesAdapter()

        init {
            with(itemBinding) {
                imagesRecyclerView.adapter = imagesAdapter
                PagerSnapHelper().attachToRecyclerView(imagesRecyclerView)
//                pagerIndicatorContainer.attachToRecyclerView(imagesRecyclerView)
            }
        }

        fun bind(model: CatalogItemEntity) {

            with(itemBinding) {

                val necessaryImages = images.firstOrNull { it.id == model.id }?.image

                imagesAdapter.submitList(necessaryImages)

                oldPriceTextView.text = itemView.context.getString(
                    R.string.price,
                    model.price.price
                )

                oldPriceTextView.paintFlags =
                    oldPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                priceTextView.text = itemView.context.getString(
                    R.string.price,
                    model.price.priceWithDiscount
                )

                discountTextView.text = itemView.context.getString(
                    R.string.discount,
                    model.price.discount
                )
                brandTextView.text = model.title
                descriptionTextView.text = model.subtitle
                ratingAndReviewsCountTextView.text = itemView.context.getString(
                    R.string.catalog_screen_rating_and_reviews_count,
                    model.feedback.rating.toString(),
                    model.feedback.count.toString()
                )

                itemView.setOnClickListener {
                    onItemClick(model)
                }

                if (model.isFavorite) {
                    addToFavoriteImageView.setBackgroundResource(com.core.ui.R.drawable.icv_favorite_on)
                } else {
                    addToFavoriteImageView.setBackgroundResource(com.core.ui.R.drawable.icv_favorite_off)
                }

                addToFavoriteImageView.setOnClickListener {
                    addToFavorite(!model.isFavorite, model)
                }
            }
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<CatalogItemEntity>() {

        override fun areItemsTheSame(
            oldItem: CatalogItemEntity,
            newItem: CatalogItemEntity,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CatalogItemEntity,
            newItem: CatalogItemEntity,
        ): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.subtitle == newItem.subtitle
                    && oldItem.price == newItem.price
                    && oldItem.feedback == newItem.feedback
                    && oldItem.tags == newItem.tags
                    && oldItem.available == newItem.available
                    && oldItem.description == newItem.description
                    && oldItem.info == newItem.info
                    && oldItem.ingredients == newItem.ingredients
                    && oldItem.isFavorite == newItem.isFavorite
        }
    }
}