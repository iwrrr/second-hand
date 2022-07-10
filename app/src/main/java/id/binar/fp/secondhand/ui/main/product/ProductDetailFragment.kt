package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.FragmentProductDetailBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.BidBottomSheet
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val productViewModel: ProductViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val isLightStatusBar: Boolean
        get() = false

    override fun setup() {
        super.setup()
        onBackClicked()
        observeDetailProduct()
    }

    private fun onBackClicked() {
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observeDetailProduct() {
        val id = arguments?.getInt("id")
        id?.let {
            productViewModel.getDetailProduct(it).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.loading.root.isVisible = true
                    }
                    is Result.Success -> {
                        binding.loading.root.isVisible = false
                        binding.tvProductName.text = result.data.name
                        binding.tvProductCategory.text = Helper.initCategory(result.data.categories)
                        binding.tvProductPrice.text = result.data.basePrice.toString()
                        binding.tvName.text = result.data.user?.fullName
                        binding.tvCity.text = result.data.user?.city
                        binding.tvProductDescription.text = result.data.description
                        binding.ivProductImage.loadImage(result.data.imageUrl)
                        binding.ivProfile.loadImage(result.data.user?.imageUrl)
                        binding.btnBid.isEnabled = result.data.status == "available"

                        initBottomSheet(result.data)
                    }
                    is Result.Error -> {
                        binding.loading.root.isVisible = false
                        Helper.showToast(requireContext(), result.error)
                    }
                }
            }
        }
    }

    private fun initBottomSheet(product: ProductDto) {
        val bottomSheet = BidBottomSheet()
        val bundle = Bundle().apply {
            putInt("id", product.id)
            putString("image", product.imageUrl)
            putString("name", product.name)
            putInt("price", product.basePrice)
        }

        bottomSheet.arguments = bundle

        binding.btnBid.setOnClickListener {
            authViewModel.getToken().observe(viewLifecycleOwner) { token ->
                if (!token.isNullOrBlank()) {
                    bottomSheet.show(parentFragmentManager, BidBottomSheet.TAG)
                } else {
                    Helper.showToast(requireContext(), "Silakan login terlebih dahulu")
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                }
            }
        }

        bottomSheet.bottomSheetCallback = object : BidBottomSheet.BottomSheetCallback {
            override fun onDismiss() {
                if (bottomSheet.statusOrder == 1) {
                    binding.btnBid.isEnabled = false
                    binding.btnBid.text = "Menunggu respon penjual"
                }
            }
        }
    }
}