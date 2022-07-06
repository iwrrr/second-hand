package id.binar.fp.secondhand.ui.main.adapter.home

import androidx.recyclerview.widget.DiffUtil
import id.binar.fp.secondhand.data.source.network.response.ProductDto

class ProductDiffutil(
    private val oldList: List<ProductDto>,
    private val newList: List<ProductDto>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].basePrice != newList[newItemPosition].basePrice -> false
            oldList[oldItemPosition].description != newList[newItemPosition].description -> false
            oldList[oldItemPosition].imageUrl != newList[newItemPosition].imageUrl -> false
            oldList[oldItemPosition].imageName != newList[newItemPosition].imageName -> false
            oldList[oldItemPosition].location != newList[newItemPosition].location -> false
            oldList[oldItemPosition].status != newList[newItemPosition].status -> false
            oldList[oldItemPosition].categories != newList[newItemPosition].categories -> false
            oldList[oldItemPosition].createdAt != newList[newItemPosition].createdAt -> false
            oldList[oldItemPosition].updatedAt != newList[newItemPosition].updatedAt -> false
            else -> true
        }
    }
}