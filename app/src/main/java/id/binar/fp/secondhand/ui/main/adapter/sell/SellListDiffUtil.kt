package id.binar.fp.secondhand.ui.main.adapter.sell

import androidx.recyclerview.widget.DiffUtil
import id.binar.fp.secondhand.util.dummy.Product

class SellListDiffUtil(
    private val oldList: List<Product>,
    private val newList: List<Product>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].status != newList[newItemPosition].status -> false
            oldList[oldItemPosition].time != newList[newItemPosition].time -> false
            oldList[oldItemPosition].price != newList[newItemPosition].price -> false
            oldList[oldItemPosition].bid != newList[newItemPosition].bid -> false
            else -> true
        }
    }
}