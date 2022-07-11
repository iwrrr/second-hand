package id.binar.fp.secondhand.ui.base

import androidx.recyclerview.widget.DiffUtil
import id.binar.fp.secondhand.domain.model.*
import id.binar.fp.secondhand.util.sameAndEqual

class BaseItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Product -> if (newItem is Product) oldItem.id.sameAndEqual(newItem) else false
            is SellerOrder -> if (newItem is SellerOrder) oldItem.id.sameAndEqual(newItem) else false
            is Category -> if (newItem is Category) oldItem.id.sameAndEqual(newItem) else false
            is History -> if (newItem is History) oldItem.id.sameAndEqual(newItem) else false
            is Notification -> if (newItem is Notification) oldItem.id.sameAndEqual(newItem) else false
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.sameAndEqual(newItem)
    }
}