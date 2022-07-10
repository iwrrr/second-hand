package id.binar.fp.secondhand.ui.main.seller.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.FragmentProductBinding
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.sell.SellerProductAdapter
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    private val productAdapter by lazy { SellerProductAdapter(::onProductClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding
        get() = FragmentProductBinding::inflate

    override fun setup() {
        super.setup()
        setupRecyclerView()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.content.rvProduct.adapter = productAdapter
        binding.content.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        observeProduct()
    }

    private fun setupRefresh() {
        swipeRefreshLayout.setOnRefreshListener { observeProduct() }
    }

    private fun observeProduct() {
        sellerViewModel.getSellerProduct().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    val availableProduct = result.data.filter { it.status == "available" }
                    if (availableProduct.isNotEmpty()) {
                        productAdapter.submitList(availableProduct)
                    } else {
                        binding.content.root.isVisible = false
                        binding.empty.root.isVisible = true
                    }
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    Helper.showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun onProductClicked(product: ProductDto) {
//            requireParentFragment().parentFragmentManager.beginTransaction().apply {
//                add(R.id.main_nav_host, ProductDetailFragment())
//                addToBackStack(null)
//                commit()
//            }
    }
}