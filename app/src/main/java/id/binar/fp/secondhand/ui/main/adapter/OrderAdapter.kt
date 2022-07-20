package id.binar.fp.secondhand.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemOrderBinding
import id.binar.fp.secondhand.domain.model.BaseType
import id.binar.fp.secondhand.domain.model.BuyerOrder
import id.binar.fp.secondhand.ui.base.BaseAdapter
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Status

@Suppress("UNCHECKED_CAST")
class OrderAdapter(
    private val context: Context,
    private val onClick: (BuyerOrder) -> Unit
) : BaseAdapter<BaseType>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyerOrderViewHolder(binding) as BaseViewHolder<BaseType>
    }

    inner class BuyerOrderViewHolder(private val binding: ItemOrderBinding) :
        BaseViewHolder<BuyerOrder>(binding.root) {

        override fun onBind(data: BuyerOrder) {
            with(binding) {
                val price = Helper.numberFormatter(data.price)
                var status = ""

                when (data.status) {
                    Status.PENDING -> {
                        status = "Menunggu"
                        tvProductStatus.setTextColor(context.getColor(R.color.colorTextPending))
                        tvProductStatus.background.setTint(context.getColor(R.color.colorBgPending))
                    }
                    Status.ACCEPTED -> {
                        status = "Selesai"
                        tvProductStatus.setTextColor(context.getColor(R.color.colorTextAccept))
                        tvProductStatus.background.setTint(context.getColor(R.color.colorBgAccept))
                    }
                    Status.DECLINED -> {
                        status = "Dibatalkan"
                        tvProductStatus.setTextColor(context.getColor(R.color.colorTextDeclined))
                        tvProductStatus.background.setTint(context.getColor(R.color.colorBgDeclined))
                    }
                }

                ivProductImage.loadImage(data.product?.imageUrl)
                tvProductName.text = data.productName
                tvProductStatus.text = status
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    price
                )

                itemView.setOnClickListener { onClick(data) }
            }
        }
    }
}