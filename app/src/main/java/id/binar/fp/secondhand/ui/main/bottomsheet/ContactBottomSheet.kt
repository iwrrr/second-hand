package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetContactBinding
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.ui.base.BaseBottomSheet
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper

class ContactBottomSheet : BaseBottomSheet<BottomSheetContactBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetContactBinding
        get() = BottomSheetContactBinding::inflate

    override fun setup() {
        super.setup()

        val order = arguments?.getParcelable<SellerOrder>("order") as SellerOrder
        binding.apply {
            val basePrice = Helper.numberFormatter(order.basePrice)
            val bidPrice = Helper.numberFormatter(order.price)
            val bidStatus = "Berhasil ditawar"

            ivProfile.loadImage(order.user?.imageUrl)
            tvName.text = order.user?.fullName
            tvCity.text = order.user?.city

            ivProductImage.loadImage(order.product?.imageUrl)
            tvProductName.text = order.productName
            tvProductPrice.text = requireContext().getString(
                R.string.text_seller_order_base_price,
                basePrice
            )
            tvProductBid.text = requireContext().getString(
                R.string.text_seller_order_bid_price,
                bidStatus,
                bidPrice
            )
        }
    }

    companion object {
        const val TAG = "ContactBottomSheet"
    }
}