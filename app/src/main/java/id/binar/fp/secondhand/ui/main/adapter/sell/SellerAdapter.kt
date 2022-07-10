package id.binar.fp.secondhand.ui.main.adapter.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.SellerOrderDto
import id.binar.fp.secondhand.databinding.ItemSellListInterestedBinding
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.sameAndEqual

class SellerAdapter(
    private val onClick: (SellerOrderDto) -> Unit
) : ListAdapter<SellerOrderDto, SellerAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSellListInterestedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSellListInterestedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SellerOrderDto) {
            with(binding) {
                var status = ""
                val date = Helper.dateFormatter(data.transactionDate)
                val price = Helper.numberFormatter(data.price)

                when (data.status) {
                    "pending" -> status = "Ditawar"
                    "accepted" -> status = "Berhasil Ditawar"
                    "declined" -> status = "Ditolak"
                }

                tvProductTime.text = date
                tvProductName.text = data.productName
                tvProductPrice.text = data.basePrice.toString()
                tvProductBid.text =
                    itemView.context.getString(R.string.text_seller_order_bid_price, status, price)
                ivProductImage.loadImage(data.product?.imageUrl)
            }

            itemView.setOnClickListener { onClick(data) }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<SellerOrderDto>() {
            override fun areItemsTheSame(
                oldItem: SellerOrderDto,
                newItem: SellerOrderDto
            ): Boolean =
                oldItem.sameAndEqual(newItem)

            override fun areContentsTheSame(
                oldItem: SellerOrderDto,
                newItem: SellerOrderDto
            ): Boolean =
                oldItem.id.sameAndEqual(newItem.id)
        }
    }
}