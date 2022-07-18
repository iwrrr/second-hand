package id.binar.fp.secondhand.ui.main.seller.interested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentInterestedBinding
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.domain.model.SellerType
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.seller.SellerAdapter
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class InterestedFragment : BaseFragment<FragmentInterestedBinding>() {

    private val sellerViewModel: SellerViewModel by viewModels()

    private val sellerAdapter by lazy { SellerAdapter<SellerOrder>(SellerType.INTERESTED) }

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

        sellerAdapter.itemClickCallback = object : SellerAdapter.ItemClickCallback<SellerOrder> {
            override fun onClick(data: SellerOrder) {
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
        sellerViewModel.getSellerOrder().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
//                    binding.loading.root.isVisible = true
                }
                is Result.Success -> {
                    hideShimmer()
                    if (result.data != null) {
//                        binding.loading.root.isVisible = false
                        binding.swipeRefresh.isRefreshing = false
                        if (result.data.isNotEmpty()) {
                            sellerAdapter.submitList(result.data)
                        } else {
                            binding.content.root.isVisible = false
                            binding.empty.root.isVisible = true
                        }
                    }
                }
                is Result.Error -> {
                    hideShimmer()
//                    binding.loading.root.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onProductClicked(order: SellerOrder) {
        requireParentFragment().parentFragmentManager.beginTransaction().apply {
            val bidderInfoFragment = BidderInfoFragment()
            val bundle = Bundle().apply {
                putParcelable("order", order)
            }
            bidderInfoFragment.arguments = bundle
            add(R.id.main_nav_host, bidderInfoFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showShimmer() {
        binding.apply {
            shimmer.root.isInvisible = false
            shimmer.root.startShimmer()
            content.root.isInvisible = true
            empty.root.isInvisible = true
        }
    }

    private fun hideShimmer() {
        binding.apply {
            shimmer.root.isInvisible = true
            shimmer.root.stopShimmer()
            content.root.isInvisible = false
            empty.root.isInvisible = true
        }
    }
}