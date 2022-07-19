package id.binar.fp.secondhand.ui.main.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentOrderBinding
import id.binar.fp.secondhand.domain.model.BuyerOrder
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.OrderAdapter
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    private val orderViewModel: OrderViewModel by viewModels()

    private val orderAdapter by lazy { OrderAdapter(requireContext(), ::onOrderClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrderBinding
        get() = FragmentOrderBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupRecyclerView()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.content.rvOrder.adapter = orderAdapter
        binding.content.rvOrder.layoutManager = LinearLayoutManager(requireContext())
        observeOrder()
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeOrder() }
    }

    private fun observeOrder() {
        orderViewModel.getBuyerOrder().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }
                is Result.Success -> {
                    hideShimmer()
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        if (result.data.isNotEmpty()) {
                            orderAdapter.submitList(result.data)
                        } else {
                            binding.empty.root.isVisible = true
                            binding.content.root.isVisible = false
                        }
                    }
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onOrderClicked(order: BuyerOrder) {
        Helper.showToast(requireContext(), order.id.toString())
    }

    private fun showShimmer() {
        binding.apply {
            shimmer.root.isInvisible = false
            shimmer.root.startShimmer()
            content.root.isInvisible = true
        }
    }

    private fun hideShimmer() {
        binding.apply {
            shimmer.root.isInvisible = true
            shimmer.root.stopShimmer()
            content.root.isInvisible = false
        }
    }
}