package id.binar.fp.secondhand.ui.main.seller.interested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentInterestedBinding
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListInterestedAdapter

@AndroidEntryPoint
class InterestedFragment : Fragment() {

    private var _binding: FragmentInterestedBinding? = null
    private val binding get() = _binding!!

    private val interestedAdapter by lazy {
        SellListInterestedAdapter {
            Toast.makeText(
                requireContext(),
                it.name,
                Toast.LENGTH_SHORT
            ).show()
            requireParentFragment().parentFragmentManager.beginTransaction().apply {
                replace(R.id.main_nav_host, BidderInfoFragment())
                addToBackStack(null)
                commit()
            }
//            findNavController().navigate(R.id.bidderInfoFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterestedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.content.root.isVisible = false
        binding.empty.root.isVisible = true

        binding.content.rvInterested.adapter = interestedAdapter
        binding.content.rvInterested.layoutManager = LinearLayoutManager(requireContext())
        binding.content.rvInterested.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

//        interestedAdapter.submitList(setDummyProducts())
    }
}