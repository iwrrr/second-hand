package id.binar.fp.secondhand.ui.main.adapter.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.ItemSellListProductBinding
import id.binar.fp.secondhand.ui.main.adapter.home.ProductDiffutil
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper

class SellerProductAdapter(
    private val onClick: (ProductDto) -> Unit
) : RecyclerView.Adapter<SellerProductAdapter.ViewHolder>() {

    private var oldProductList = emptyList<ProductDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSellListProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldProductList[position])
    }

    override fun getItemCount(): Int = oldProductList.size

    fun submitList(newProductList: List<ProductDto>) {
        val diffUtil = ProductDiffutil(oldProductList, newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldProductList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemSellListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDto) {
            with(binding) {
                tvProductName.text = product.name
                tvProductCategory.text = Helper.initCategory(product.categories)
                tvProductPrice.text = product.basePrice.toString()
                ivProductImage.loadImage(product.imageUrl)
                itemView.setOnClickListener { onClick(product) }
            }
        }
    }
}