package com.core.ui.base


import android.annotation.SuppressLint
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.core.common.extensions.indexOfFirstOrNull
import com.core.common.marker.ListItem
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import java.util.*


class BaseDelegateAdapter<T : List<Any>?> : AbsDelegationAdapter<T> {
    constructor() : super()
    constructor(delegatesManager: AdapterDelegatesManager<T>) : super(delegatesManager)
    constructor(vararg delegates: AdapterDelegate<T>) : super(*delegates)

    var loadMoreCallback: (() -> Unit)? = null
    private var loadSize: Int? = null

    var areItemsTheSameCallBack: ( (oldItem: Any?, newItem: Any?) -> Boolean ) ? = null
    var getChangePayloadCallBack: ( (oldItem: Any?, newItem: Any?) -> Any? ) ? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        checkPagination(position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        checkPagination(position)
    }

    private fun checkPagination(position: Int) {
        if (position == 0) return
        val itemsSize = items?.size ?: 0
        val lastSize = loadSize

        if (position + 5 >= itemsSize && (lastSize == null || lastSize < itemsSize || lastSize > itemsSize)) {
            loadSize = itemsSize
            loadMoreCallback?.invoke()
        }
    }

    override fun getItemCount() = items?.size ?: 0
    fun getItem(position: Int) = items?.get(position)
    fun getPosition(item: Any) = items?.indexOf(item) ?: 0
    fun removeItem(item: Any) = (items as? MutableList<*>)?.indexOfFirstOrNull { it == item }
        ?.let { position -> removeItem(position) }

    fun removeItem(position: Int) = (items as? MutableList<*>)?.removeAt(position)
        .also { notifyItemRemoved(position) }

    @SuppressLint("NotifyDataSetChanged")
    fun swapItems(newItems: List<ListItem>, withDiff: Boolean = true) {
        if (withDiff) {
            val diffResult =
                DiffUtil.calculateDiff(
                    ListViewModelDiffCallback(
                        items,
                        newItems,
                        areItemsTheSameCallBack
                    )
                )
            setList(newItems)
            diffResult.dispatchUpdatesTo(this)
        } else {
            setList(newItems)
            notifyDataSetChanged()
        }
    }

    private fun setList(newItems: List<ListItem>) {
        if (items == null) setItems(mutableListOf<T>() as T)
        (items as? MutableList<Any>)?.apply {
            clear()
            addAll(newItems)
        }
    }

    fun addItems(newItems: List<ListItem>, position: Int? = null) {
        val newList = mutableListOf<Any>().apply { (items as? List<Any>)?.let(::addAll) }
        position?.let { newList.addAll(it, newItems) } ?: newList.addAll(newItems)
        val diffResult = DiffUtil.calculateDiff(
            ListViewModelDiffCallback(
                items,
                newList,
                areItemsTheSameCallBack
            )
        )
        (this.items as? MutableList<Any>)?.apply {
            clear()
            addAll(items as List<Any>)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        val newItems = mutableListOf<ListItem>().apply { (items as? List<ListItem>)?.let(::addAll) }
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newItems, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(newItems, i, i - 1)
            }
        }
        val diffResult = DiffUtil.calculateDiff(ListViewModelDiffCallback(items, newItems))
        setList(newItems)
        diffResult.dispatchUpdatesTo(this)
        return true
    }
    fun skipLoadSize(){
        loadSize = null
    }
}

interface OnListItemClickListener {
    fun onItemClicked(listItem: ListItem)
}

class ListViewModelDiffCallback(
    private var oldList: List<*>? = null,
    private var newList: List<*>? = null,
    private var areItemsTheSameCallBack: ( (oldItem: Any?,newItem: Any?) -> Boolean ) ? = null,
    private var getChangePayloadCallBack: ( (oldItem: Any?, newItem: Any?) -> Boolean ) ? = null
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList?.size ?: 0
    override fun getNewListSize() = newList?.size ?: 0
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areItemsTheSameCallBack?.invoke(
            oldList?.get(oldItemPosition),
            newList?.get(newItemPosition)
        )
            ?: areContentsTheSame(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList?.get(oldItemPosition)
        val newModel = newList?.get(newItemPosition)
        return oldModel?.equals(newModel) ?: false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return getChangePayloadCallBack?.invoke(
            oldList?.get(oldItemPosition),
            newList?.get(newItemPosition)
        ) ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }
}