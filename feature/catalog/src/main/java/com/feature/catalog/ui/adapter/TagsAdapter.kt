package com.feature.catalog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.core.ui.databinding.ItemTagBinding
import com.feature.catalog.domain.models.TagModel

class TagsAdapter(
    private val onTagClick: (model: TagModel) -> Unit,
    private val cancelSort: () -> Unit
) : ListAdapter<TagModel, TagsAdapter.TagViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TagViewHolder(
        itemBinding = ItemTagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TagViewHolder(val itemBinding: ItemTagBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(model: TagModel) {

            with(itemBinding) {

                tagTextView.text = model.name

                if (model.isClicked) {

                    tagContainer.setBackgroundResource(com.core.ui.R.drawable.background_dark_blue_round_100)
                    tagTextView.setTextColor(ContextCompat.getColor(itemView.context, com.core.ui.R.color.white))
                    cancelImageView.visibility = View.VISIBLE
                } else {

                    tagContainer.setBackgroundResource(com.core.ui.R.drawable.background_light_gray_round_100)
                    tagTextView.setTextColor(ContextCompat.getColor(itemView.context, com.core.ui.R.color.light_grey))
                    cancelImageView.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    onTagClick(model)
                }

                cancelImageView.setOnClickListener {
                    cancelSort()
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TagModel>() {
        override fun areItemsTheSame(
            oldItem: TagModel,
            newItem: TagModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TagModel,
            newItem: TagModel,
        ): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.type == newItem.type
                    && oldItem.isClicked == newItem.isClicked
        }
    }
}