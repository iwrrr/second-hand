package id.binar.fp.secondhand.ui.main.seller.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentProductBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.model.SellerType
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.seller.SellerAdapter
import id.binar.fp.secondhand.ui.main.product.EditProductFragment
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    private val sellerAdapter by lazy { SellerAdapter(SellerType.PRODUCT, ::onProductClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding
        get() = FragmentProductBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupRecyclerView()
        setupRefresh()
//        setupSwipeToDelete()
    }

    private fun setupRecyclerView() {
        binding.content.rvProduct.adapter = sellerAdapter
        binding.content.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        observeProduct()
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeProduct() }
    }

    private fun observeProduct() {
        sellerViewModel.getSellerProduct().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    val availableProduct =
                        result.data.filter { it.status == Status.PRODUCT_AVAILABLE }
                    if (availableProduct.isNotEmpty()) {
                        sellerAdapter.submitList(availableProduct)
                    } else {
                        binding.content.root.isVisible = false
                        binding.empty.root.isVisible = true
                    }
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun onProductClicked(product: Product) {
        val editProductFragment = EditProductFragment()
        val bundle = Bundle().apply {
            putInt("product_id", product.id)
        }
        editProductFragment.arguments = bundle
        requireParentFragment().parentFragmentManager.beginTransaction().apply {
            add(R.id.main_nav_host, editProductFragment)
            addToBackStack(null)
            commit()
        }
    }
}