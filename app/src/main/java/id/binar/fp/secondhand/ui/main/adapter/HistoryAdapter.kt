package id.binar.fp.secondhand.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemHistoryBinding
import id.binar.fp.secondhand.domain.model.BaseType
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.ui.base.BaseAdapter
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Status

@Suppress("UNCHECKED_CAST")
class HistoryAdapter(
    private val context: Context,
    private val onClick: (History) -> Unit
) : BaseAdapter<BaseType>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding) as BaseViewHolder<BaseType>
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        BaseViewHolder<History>(binding.root) {

        override fun onBind(data: History) {
            with(binding) {
                val date = Helper.dateFormatter(data.updatedAt)
                val price = Helper.numberFormatter(data.price)
                var status = ""

                when (data.status) {
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

                ivProductImage.loadImage(data.imageUrl)
                tvProductName.text = data.productName
                tvProductStatus.text = status
                tvProductTime.text = date
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    price
                )

                itemView.setOnClickListener { onClick(data) }
            }
        }
    }
}