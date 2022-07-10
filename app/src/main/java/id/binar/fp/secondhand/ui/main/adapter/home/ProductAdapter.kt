package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.BaseType
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemProductBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.ui.base.BaseAdapter
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Status

@Suppress("UNCHECKED_CAST")
class ProductAdapter(
    private val onClick: (Product) -> Unit
) : BaseAdapter<BaseType>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding) as BaseViewHolder<BaseType>
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun onBind(data: Product) {
            if (data.status == Status.AVAILABLE) {
                with(binding) {
                    val price = Helper.numberFormatter(data.basePrice)
                    tvProductName.text = data.name
                    tvProductCategory.text = Helper.initCategory(data.categories)
                    tvProductPrice.text = itemView.context.getString(
                        R.string.text_seller_order_base_price,
                        price
                    )
                    ivProductImage.loadImage(data.imageUrl)
                    itemView.setOnClickListener { onClick(data) }
                }
            }
        }
    }
}