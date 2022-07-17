package id.binar.fp.secondhand.ui.main.seller.sold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentSoldBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.model.SellerType
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.seller.SellerAdapter
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status

@AndroidEntryPoint
class SoldFragment : BaseFragment<FragmentSoldBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    private val sellerAdapter by lazy { SellerAdapter<Product>(SellerType.SOLD) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSoldBinding
        get() = FragmentSoldBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupRecyclerView()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.content.rvSold.adapter = sellerAdapter
        binding.content.rvSold.layoutManager = GridLayoutManager(requireContext(), 2)

        sellerAdapter.itemClickCallback = object : SellerAdapter.ItemClickCallback<Product> {
            override fun onClick(data: Product) {
                onProductClicked(data)
            }

            override fun onDelete(id: Int) {}
        }

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
                    if (result.data != null) {
                        binding.loading.root.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                        val availableProduct = result.data.filter { it.status == Status.SOLD }
                        if (availableProduct.isNotEmpty()) {
                            sellerAdapter.submitList(availableProduct)
                        } else {
                            binding.content.root.isVisible = false
                            binding.empty.root.isVisible = true
                        }
                    }
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onProductClicked(product: Product) {
//            requireParentFragment().parentFragmentManager.beginTransaction().apply {
//                add(R.id.main_nav_host, ProductDetailFragment())
//                addToBackStack(null)
//                commit()
//            }
    }
}