package id.binar.fp.secondhand.ui.main.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemNotificationBinding
import id.binar.fp.secondhand.domain.model.BaseType
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.ui.base.BaseAdapter
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Status

@Suppress("UNCHECKED_CAST")
class NotificationAdapter(
    private val onClick: (Notification) -> Unit
) : BaseAdapter<BaseType>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding) as BaseViewHolder<BaseType>
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        BaseViewHolder<Notification>(binding.root) {

        override fun onBind(data: Notification, position: Int) {
            with(binding) {
                val date = Helper.dateFormatter(data.createdAt)
                val basePrice = Helper.numberFormatter(data.basePrice?.toInt())
                val bidPrice: String
                val bidStatus: String
                var status = ""
                val desc: String

                tvDesc.isVisible = false
                tvProductBid.isVisible = true

                when (data.status) {
                    Status.CREATE -> {
                        status = "Berhasil diterbitkan"
                        tvProductBid.isVisible = false
                    }
                    Status.BID -> {
                        status = "Penawaran produk"
                        bidStatus = "Ditawar"
                        bidPrice = Helper.numberFormatter(data.bidPrice)
                        tvProductBid.text = itemView.context.getString(
                            R.string.text_seller_order_bid_price,
                            bidStatus,
                            bidPrice
                        )
                    }
                    Status.ACCEPTED -> {
                        status = "Penawaran produk"
                        bidStatus = "Berhasil ditawar"
                        bidPrice = Helper.numberFormatter(data.bidPrice)
                        desc = "Kamu akan segera dihubungi oleh penjual via whatsapp"
                        tvDesc.isVisible = true
                        tvDesc.text = desc
                        tvProductBid.text = itemView.context.getString(
                            R.string.text_seller_order_bid_price,
                            bidStatus,
                            bidPrice
                        )
                        tvProductBid.paintFlags =
                            tvProductBid.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                    Status.DECLINED -> {
                        status = "Penawaran ditolak"
                        bidStatus = "Ditolak"
                        bidPrice = Helper.numberFormatter(data.bidPrice)
                        tvProductBid.text = itemView.context.getString(
                            R.string.text_seller_order_bid_price,
                            bidStatus,
                            bidPrice
                        )
                        tvProductBid.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }

                tvProductStatus.text = status
                tvProductTime.text = date
                tvProductName.text = data.productName
                tvProductPrice.text = itemView.context.getString(
                    R.string.text_seller_order_base_price,
                    basePrice
                )
                ivProductImage.loadImage(data.imageUrl)
                ivProductRead.isVisible = !data.read

                itemView.setOnClickListener {
                    onClick(data)
                    notifyItemChanged(position)
                }
            }
        }
    }
}