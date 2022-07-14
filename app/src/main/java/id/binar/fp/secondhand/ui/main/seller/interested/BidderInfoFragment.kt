package id.binar.fp.secondhand.ui.main.seller.interested

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentBidderInfoBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.ContactBottomSheet
import id.binar.fp.secondhand.ui.main.bottomsheet.StatusBottomSheet
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status

@AndroidEntryPoint
class BidderInfoFragment : BaseFragment<FragmentBidderInfoBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBidderInfoBinding
        get() = FragmentBidderInfoBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private var order: SellerOrder? = null
    private var productId: Int = 0
    private var orderId: Int = 0
    private var status: String = ""

    override fun setup() {
        super.setup()
        initData()
        setupButtonStatus()
    }

    override fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Info Penawar"
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setupButtonStatus() {
        binding.contentBtnNotAcceptedYet.btnReject.setOnClickListener {
            status = Status.ORDER_DECLINED
            observeOrder(status, ::declined)
        }

        binding.contentBtnNotAcceptedYet.btnAccept.setOnClickListener {
            status = Status.ORDER_ACCEPTED
            observeOrder(status, ::accepted)
        }

        binding.contentBtnAccepted.btnContact.setOnClickListener {
            setupBottomSheetContact()
        }

        binding.contentBtnAccepted.btnStatus.setOnClickListener {
            setupBottomSheetStatus()
        }
    }

    private fun setupBottomSheetContact() {
        val bottomSheet = ContactBottomSheet()
        val bundle = Bundle().apply {
            putParcelable("order", order)
        }
        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager, ContactBottomSheet.TAG)
    }

    private fun setupBottomSheetStatus() {
        val bottomSheet = StatusBottomSheet()
        val bundle = Bundle().apply {
            putInt("product_id", productId)
        }

        bottomSheet.arguments = bundle
        bottomSheet.show(childFragmentManager, StatusBottomSheet.TAG)

        bottomSheet.bottomSheetCallback = object : StatusBottomSheet.BottomSheetCallback {
            override fun onStatusUpdate(productId: Int, status: String) {
                when (status) {
                    Status.CANCEL -> {
                        observeProductStatus(productId, Status.PRODUCT_AVAILABLE, ::available)
                        observeOrder(Status.ORDER_DECLINED, ::declined)
                    }
                    Status.SUCCESS -> {
                        observeProductStatus(productId, Status.PRODUCT_SOLD, ::sold)
                    }
                }
                bottomSheet.dismiss()
            }
        }
    }

    private fun initData() {
        observeProduct()
    }

    private fun observeProduct() {
//        val orderId = arguments?.getInt("order_id") as Int
        val order = arguments?.getParcelable<SellerOrder>("order") as SellerOrder
        this.orderId = order.id
        sellerViewModel.getSellerOrderById(order.id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Success -> {
                    this.order = result.data
                    this.productId = result.data.productId as Int
                    binding.apply {
                        progressBar.isVisible = false
                        ivProfile.loadImage(result.data.user?.imageUrl)
                        tvName.text = result.data.user?.fullName
                        tvCity.text = result.data.user?.city

                        contentBtnNotAcceptedYet.root.isVisible = true
                        contentBtnAccepted.root.isVisible = false
                        contentProduct.apply {
                            val date = Helper.dateFormatter(result.data.transactionDate)
                            val basePrice = Helper.numberFormatter(result.data.basePrice)
                            val bidPrice = Helper.numberFormatter(result.data.price)

                            var productStatus = ""
                            var bidStatus = ""

                            when (result.data.status) {
                                Status.ORDER_PENDING -> {
                                    productStatus = "Penawaran produk"
                                    bidStatus = "Ditawar"
                                    tvProductBid.paintFlags =
                                        tvProductBid.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                                }
                                Status.ORDER_ACCEPTED -> {
                                    if (result.data.product?.status == Status.PRODUCT_SOLD) {
                                        productStatus = "Berhasil terjual"
                                        contentBtnAccepted.root.isVisible = false
                                    } else {
                                        productStatus = "Penawaran produk"
                                        contentBtnAccepted.root.isVisible = true
                                    }
                                    bidStatus = "Berhasil Ditawar"
                                    contentBtnNotAcceptedYet.root.isVisible = false
                                }
                                Status.ORDER_DECLINED -> {
                                    productStatus = "Penawaran ditolak"
                                    bidStatus = "Ditolak"
                                    tvProductBid.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                                    contentBtnNotAcceptedYet.root.isVisible = false
                                    contentBtnAccepted.root.isVisible = false
                                }
                            }

                            ivProductImage.loadImage(result.data.product?.imageUrl)
                            tvProductTime.text = date
                            tvProductName.text = result.data.productName
                            tvProductStatus.text = productStatus
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
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Helper.showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun observeProductStatus(
        productId: Int,
        status: String,
        statusProduct: (Result<Product>) -> Unit
    ) {
        sellerViewModel.updateSellerProductById(productId, status)
            .observe(viewLifecycleOwner, statusProduct)
    }

    private fun observeOrder(status: String, statusOrder: (Result<SellerOrder>) -> Unit) {
        sellerViewModel.updateSellerOrderById(orderId, status)
            .observe(viewLifecycleOwner, statusOrder)
    }

    private fun available(result: Result<Product>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Result.Success -> {
                binding.apply {
                    progressBar.isVisible = false
                    contentBtnNotAcceptedYet.root.isVisible = false
                    contentBtnAccepted.root.isVisible = true
                    contentProduct.tvProductStatus.text = "Penawaran produk"
                }
            }
            is Result.Error -> {
                binding.progressBar.isVisible = false
                Helper.showToast(requireContext(), result.error)
            }
        }
    }

    private fun sold(result: Result<Product>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Result.Success -> {
                binding.apply {
                    progressBar.isVisible = false
                    contentBtnNotAcceptedYet.root.isVisible = false
                    contentBtnAccepted.root.isVisible = false
                    contentProduct.tvProductStatus.text = "Berhasil terjual"
                }
                Helper.showToast(requireContext(), "Status produk berhasil diperbarui")
            }
            is Result.Error -> {
                binding.progressBar.isVisible = false
                Helper.showToast(requireContext(), result.error)
            }
        }
    }

    private fun accepted(result: Result<SellerOrder>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Result.Success -> {
                binding.apply {
                    progressBar.isVisible = false
                    contentBtnNotAcceptedYet.root.isVisible = false
                    contentBtnAccepted.root.isVisible = true
                    contentProduct.apply {
                        val date = Helper.dateFormatter(result.data.transactionDate)
                        val bidPrice = Helper.numberFormatter(result.data.price)

                        var productStatus = ""
                        var bidStatus = ""

                        when (result.data.status) {
                            Status.ORDER_ACCEPTED -> {
                                if (result.data.product?.status == Status.PRODUCT_SOLD) {
                                    productStatus = "Berhasil terjual"
                                    observeProductStatus(
                                        result.data.productId as Int,
                                        Status.PRODUCT_SOLD,
                                        ::sold
                                    )
                                } else {
                                    productStatus = "Penawaran produk"
                                    observeProductStatus(
                                        result.data.productId as Int,
                                        Status.PRODUCT_AVAILABLE,
                                        ::available
                                    )
                                }
                                bidStatus = "Berhasil Ditawar"
                            }
                        }

                        tvProductTime.text = date
                        tvProductStatus.text = productStatus
                        tvProductBid.text = requireContext().getString(
                            R.string.text_seller_order_bid_price,
                            bidStatus,
                            bidPrice
                        )
                    }
                }
                Helper.showToast(requireContext(), "Status order berhasil diperbarui")
                setupBottomSheetContact()
            }
            is Result.Error -> {
                binding.progressBar.isVisible = false
                Helper.showToast(requireContext(), result.error)
            }
        }
    }

    private fun declined(result: Result<SellerOrder>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.isVisible = true
            }
            is Result.Success -> {
                binding.apply {
                    progressBar.isVisible = false
                    contentBtnNotAcceptedYet.root.isVisible = false
                    contentBtnAccepted.root.isVisible = false
                    contentProduct.apply {
                        val date = Helper.dateFormatter(result.data.transactionDate)
                        val bidPrice = Helper.numberFormatter(result.data.price)

                        var productStatus = ""
                        var bidStatus = ""

                        when (result.data.status) {
                            Status.ORDER_DECLINED -> {
                                productStatus = "Penawaran ditolak"
                                bidStatus = "Ditolak"
                                tvProductBid.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                            }
                        }

                        tvProductTime.text = date
                        tvProductStatus.text = productStatus
                        tvProductBid.text = requireContext().getString(
                            R.string.text_seller_order_bid_price,
                            bidStatus,
                            bidPrice
                        )
                    }
                }
                Helper.showToast(requireContext(), "Status order berhasil diperbarui")
            }
            is Result.Error -> {
                binding.progressBar.isVisible = false
                Helper.showToast(requireContext(), result.error)
            }
        }
    }
}