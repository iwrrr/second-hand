package id.binar.fp.secondhand.ui.main.adapter.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.databinding.ItemSellListInterestedBinding
import id.binar.fp.secondhand.util.dummy.Product

class SellListInterestedAdapter(
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<SellListInterestedAdapter.ViewHolder>() {

    private var oldProductList = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSellListInterestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldProductList[position])
    }

    override fun getItemCount(): Int = oldProductList.size

    fun submitList(newProductList: List<Product>) {
        val diffUtil = SellListDiffUtil(oldProductList, newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldProductList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemSellListInterestedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvProductStatus.text = product.status
                tvProductTime.text = product.time
                tvProductName.text = product.name
                tvProductPrice.text = product.price
                tvProductBid.text = product.bid
            }

            itemView.setOnClickListener { onClick(product) }
        }
    }
}