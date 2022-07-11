package id.binar.fp.secondhand.ui.main.adapter.seller

import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemSellerInterestedBinding
import id.binar.fp.secondhand.databinding.ItemSellerProductBinding
import id.binar.fp.secondhand.domain.model.BaseType
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.domain.model.SellerType
import id.binar.fp.secondhand.ui.base.BaseAdapter
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Status

@Suppress("UNCHECKED_CAST")
class SellerAdapter<T>(
    private val type: SellerType,
    private val onClick: (T) -> Unit
) : BaseAdapter<BaseType>() {

    override fun getItemId(position: Int): Long {
        return when (getItem(position)) {
            is Product -> (getItem(position) as Product).id.toLong()
            is SellerOrder -> (getItem(position) as SellerOrder).id.toLong()
            else -> position.toLong()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            SellerType.PRODUCT -> ProductViewHolder(
                ItemSellerProductBinding.inflate(inflater, parent, false)
            )
            SellerType.INTERESTED -> InterestedViewHolder(
                ItemSellerInterestedBinding.inflate(inflater, parent, false)
            )
            SellerType.SOLD -> SoldViewHolder(
                ItemSellerProductBinding.inflate(inflater, parent, false)
            )
        } as BaseViewHolder<BaseType>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseType>, position: Int) {
        when (type) {
            SellerType.PRODUCT -> {
                (holder as SellerAdapter<Product>.ProductViewHolder).onBind(getItem(position) as Product)
            }
            SellerType.INTERESTED -> {
                (holder as SellerAdapter<SellerOrder>.InterestedViewHolder).onBind(getItem(position) as SellerOrder)
            }
            SellerType.SOLD -> {
                (holder as SellerAdapter<Product>.SoldViewHolder).onBind(getItem(position) as Product)
            }
        }
    }

    inner class ProductViewHolder(private val binding: ItemSellerProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun onBind(data: Product) {
            with(binding) {
                val price = Helper.numberFormatter(data.basePrice)
                tvProductName.text = data.name
                tvProductCategory.text = Helper.initCategory(data.categories)
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    price
                )
                ivProductImage.loadImage(data.imageUrl)
                itemView.setOnClickListener { onClick(data as T) }
            }
        }
    }

    inner class InterestedViewHolder(private val binding: ItemSellerInterestedBinding) :
        BaseViewHolder<SellerOrder>(binding.root) {

        override fun onBind(data: SellerOrder) {
            with(binding) {
                var status = ""
                val date = Helper.dateFormatter(data.transactionDate)
                val basePrice = Helper.numberFormatter(data.basePrice)
                val bidPrice = Helper.numberFormatter(data.price)

                when (data.status) {
                    Status.PENDING -> status = "Ditawar"
                    Status.ACCEPTED -> status = "Berhasil Ditawar"
                    Status.DECLINED -> status = "Ditolak"
                }

                tvProductTime.text = date
                tvProductName.text = data.productName
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    basePrice
                )
                tvProductBid.text = itemView.context.getString(
                    R.string.text_seller_order_bid_price,
                    status,
                    bidPrice
                )
                ivProductImage.loadImage(data.product?.imageUrl)
            }

            itemView.setOnClickListener { onClick(data as T) }
        }
    }

    inner class SoldViewHolder(private val binding: ItemSellerProductBinding) :
        BaseViewHolder<Product>(binding.root) {

        override fun onBind(data: Product) {
            with(binding) {
                val price = Helper.numberFormatter(data.basePrice)
                tvProductName.text = data.name
                tvProductCategory.text = Helper.initCategory(data.categories)
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    price
                )
                ivProductImage.loadImage(data.imageUrl)
                itemView.setOnClickListener { onClick(data as T) }
            }
        }
    }
}