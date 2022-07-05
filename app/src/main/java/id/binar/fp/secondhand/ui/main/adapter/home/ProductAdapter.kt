package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.ItemSellListProductBinding
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper

class ProductAdapter(private val onClick:(ProductDto)->Unit
    ):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var oldProductList = emptyList<ProductDto>()

        inner class ProductViewHolder(private val binding: ItemSellListProductBinding):
                RecyclerView.ViewHolder(binding.root){
                    fun bind(listProduct : ProductDto){
                        binding.tvProductName.text = listProduct.name
                        binding.tvProductCategory.text = Helper.initCategory(listProduct.categories)
                        binding.tvProductPrice.text = listProduct.basePrice.toString()
                        binding.ivProductImage.loadImage(listProduct.imageUrl)
                        itemView.setOnClickListener { onClick(listProduct) }
                    }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       val binding = ItemSellListProductBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return ProductViewHolder((binding))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       holder.bind(oldProductList[position])
    }

    override fun getItemCount(): Int = oldProductList.size

    fun submitList(newProductList : List<ProductDto>){
        val diffUtil = ProductDiffutil(oldProductList,newProductList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldProductList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }


}