package id.binar.fp.secondhand.ui.main.bottomsheet

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.BottomSheetBidBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet
import id.binar.fp.secondhand.ui.main.product.ProductViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class BidBottomSheet : BaseBottomSheet<BottomSheetBidBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    var statusOrder: Int = 0
    var bottomSheetCallback: BottomSheetCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetBidBinding
        get() = BottomSheetBidBinding::inflate

    override fun setup() {
        val productId = arguments?.getInt("id")
        val image = arguments?.getString("image")
        val name = arguments?.getString("name")
        val price = arguments?.getInt("price")

        binding.ivProductImage.loadImage(image)
        binding.tvProductName.text = name
        binding.tvProductPrice.text = price.toString()

        binding.btnBid.setOnClickListener {
            val bidPrice = binding.etProductBidPrice.text.toString()

            if (bidPrice.isBlank()) {
                binding.etlProductBidPrice.error = "Harga tawar tidak boleh kosong"
                binding.etlProductBidPrice.requestFocus()
                return@setOnClickListener
            }

            productId?.let { id ->
                viewModel.addBuyerOrder(id, bidPrice).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is Result.Success -> {
                            binding.progressBar.isVisible = false
                            statusOrder = 1
                            dialog?.dismiss()
                            Helper.showToast(requireContext(), "Produk berhasil ditawar!")
                        }
                        is Result.Error -> {
                            binding.progressBar.isVisible = false
                            statusOrder = 0
                            dialog?.dismiss()
                            Helper.showToast(requireContext(), result.error)
                        }
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        bottomSheetCallback?.onDismiss()
    }

    interface BottomSheetCallback {
        fun onDismiss()
    }

    companion object {
        const val TAG = "BidBottomSheet"
    }
}