package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentDetailProductBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.BidBottomSheet
import id.binar.fp.secondhand.ui.main.home.SearchFragment
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailProductFragment : BaseFragment<FragmentDetailProductBinding>() {

    private val productViewModel: ProductViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailProductBinding
        get() = FragmentDetailProductBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupSearch()
        setupRefresh()
        onBackClicked()
        observeDetailProduct()
    }

    private fun setupSearch() {
        val imm =
            ContextCompat.getSystemService(requireView().context, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(requireView().windowToken, 0)

        binding.toolbar.etSearch.isCursorVisible = false
        binding.toolbar.etSearch.isFocusable = false
        binding.toolbar.etSearch.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, SearchFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeDetailProduct() }
    }

    private fun observeDetailProduct() {
        val id = arguments?.getInt("id") as Int
        productViewModel.getDetailProduct(id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showError(false)
                    showShimmer(true)
                    binding.swipeRefresh.isRefreshing = false
                }
                is Result.Success -> {
                    showError(false)
                    showShimmer(false)
                    binding.swipeRefresh.isRefreshing = false
                    if (result.data != null) {
                        binding.content.apply {
                            val price = Helper.numberFormatter(result.data.basePrice)

                            ivProfile.loadImage(result.data.user?.imageUrl)
                            tvName.text = result.data.user?.fullName
                            tvCity.text = result.data.user?.city

                            tvProductName.text = result.data.name
                            tvProductCategory.text =
                                Helper.initCategory(result.data.categories)
                            tvProductPrice.text = requireContext().getString(
                                R.string.text_seller_order_base_price,
                                price
                            )
                            tvProductDescription.text = result.data.description
                            ivProductImage.loadImage(result.data.imageUrl)
                        }

                        binding.btnBid.isEnabled = result.data.status == Status.AVAILABLE
                        initBottomSheet(result.data)
                    }
                }
                is Result.Error -> {
                    showShimmer(false)
                    binding.swipeRefresh.isRefreshing = false
                    if (result.message != null) {
                        if ("host" in result.message || "connect" in result.message) {
                            showError(true)
                        } else {
                            Helper.showToast(requireContext(), result.message)
                        }
                    }
                }
            }
        }
    }

    private fun initBottomSheet(product: Product) {
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
                    Helper.showSnackbar(
                        requireContext(),
                        binding.root,
                        getString(R.string.text_order_success)
                    )
                }
            }
        }
    }

    private fun onBackClicked() {
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun showShimmer(isLoading: Boolean) {
        binding.apply {
            shimmer.root.isVisible = isLoading
            shimmer.root.showShimmer(isLoading)
            content.root.isVisible = !isLoading
            layoutButton.isVisible = !isLoading
        }
    }

    private fun showError(isError: Boolean) {
        binding.apply {
            toolbar.root.isVisible = !isError
            content.root.isVisible = !isError
            layoutButton.isVisible = !isError
            error.root.isVisible = isError
        }
    }
}