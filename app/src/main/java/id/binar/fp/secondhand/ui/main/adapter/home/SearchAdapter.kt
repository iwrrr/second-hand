package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.ItemSearchProductBinding
import id.binar.fp.secondhand.util.Extensions.loadImage

class SearchAdapter(
    private val onClick: (ProductDto) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var oldProductList = emptyList<ProductDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchProductBinding.inflate(
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

    fun submitList(newProductList: List<ProductDto>) {
        val diffUtil = SearchDiffUtil(oldProductList, newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldProductList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDto) {
            with(binding) {
                tvProductName.text = product.name
                tvProductPrice.text = product.basePrice.toString()
                ivProductImage.loadImage(product.imageUrl)
            }

            itemView.setOnClickListener { onClick(product) }
        }
    }
}