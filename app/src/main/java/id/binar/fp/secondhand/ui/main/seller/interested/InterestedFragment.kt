package id.binar.fp.secondhand.ui.main.seller.interested

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentInterestedBinding
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListInterestedAdapter
import id.binar.fp.secondhand.util.dummy.DataDummy.setDummyProducts
import id.binar.fp.secondhand.util.dummy.Product

@AndroidEntryPoint
class InterestedFragment : BaseFragment<FragmentInterestedBinding>() {

    private val interestedAdapter by lazy { SellListInterestedAdapter(::onProductClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInterestedBinding
        get() = FragmentInterestedBinding::inflate

    override fun setup() {
        super.setup()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
//        binding.content.root.isVisible = false
//        binding.empty.root.isVisible = true

        binding.content.rvInterested.adapter = interestedAdapter
        binding.content.rvInterested.layoutManager = LinearLayoutManager(requireContext())
        binding.content.rvInterested.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        interestedAdapter.submitList(setDummyProducts())
    }

    private fun onProductClicked(product: Product) {
        requireParentFragment().parentFragmentManager.beginTransaction().apply {
            add(R.id.main_nav_host, BidderInfoFragment())
            addToBackStack(null)
            commit()
        }
    }
}