package id.binar.fp.secondhand.ui.main.adapter.sell

import androidx.recyclerview.widget.DiffUtil
import id.binar.fp.secondhand.data.source.network.response.SellerOrderDto

class SellListDiffUtil(
    private val oldList: List<SellerOrderDto>,
    private val newList: List<SellerOrderDto>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].status != newList[newItemPosition].status -> false
            oldList[oldItemPosition].price != newList[newItemPosition].price -> false
            else -> true
        }
    }
}