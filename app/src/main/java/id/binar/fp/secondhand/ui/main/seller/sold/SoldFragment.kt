package id.binar.fp.secondhand.ui.main.seller.sold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentSoldBinding
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.sell.SellerAdapter
import id.binar.fp.secondhand.util.dummy.Product

@AndroidEntryPoint
class SoldFragment : BaseFragment<FragmentSoldBinding>() {

    private val interestedAdapter by lazy { SellerAdapter { } }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSoldBinding
        get() = FragmentSoldBinding::inflate

    override fun setup() {
        super.setup()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.content.root.isVisible = false
        binding.empty.root.isVisible = true

        binding.content.rvSold.adapter = interestedAdapter
        binding.content.rvSold.layoutManager = LinearLayoutManager(requireContext())
        binding.content.rvSold.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

//        interestedAdapter.submitList(setDummyProducts())
    }

    private fun onProductClicked(product: Product) {
//        requireParentFragment().parentFragmentManager.beginTransaction().apply {
//            replace(R.id.main_nav_host, BidderInfoFragment())
//            addToBackStack(null)
//            commit()
//        }
    }
}