package id.binar.fp.secondhand.ui.main.seller.interested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.SellerType
import id.binar.fp.secondhand.databinding.FragmentInterestedBinding
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.seller.SellerAdapter
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class InterestedFragment : BaseFragment<FragmentInterestedBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    private val sellerAdapter by lazy { SellerAdapter(SellerType.INTERESTED, ::onProductClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInterestedBinding
        get() = FragmentInterestedBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupRecyclerView()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.content.rvInterested.apply {
            adapter = sellerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(itemDecoration)
        }

        observeProduct()
    }

    private fun setupRefresh() {
        swipeRefreshLayout.setOnRefreshListener { observeProduct() }
    }

    private fun observeProduct() {
        sellerViewModel.getSellerOrder().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    if (result.data.isNotEmpty()) {
                        sellerAdapter.submitList(result.data)
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

    private fun onProductClicked(product: SellerOrder) {
        requireParentFragment().parentFragmentManager.beginTransaction().apply {
            val bidderInfoFragment = BidderInfoFragment()
            val bundle = Bundle().apply {
                putParcelable("product", product)
            }
            bidderInfoFragment.arguments = bundle
            add(R.id.main_nav_host, bidderInfoFragment)
            addToBackStack(null)
            commit()
        }
    }
}