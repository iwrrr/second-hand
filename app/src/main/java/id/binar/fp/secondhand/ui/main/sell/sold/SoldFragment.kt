package id.binar.fp.secondhand.ui.main.sell.sold

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentSoldBinding
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListInterestedAdapter
import id.binar.fp.secondhand.util.dummy.DataDummy.setDummyProducts

@AndroidEntryPoint
class SoldFragment : Fragment() {

    private var _binding: FragmentSoldBinding? = null
    private val binding get() = _binding!!

    private val interestedAdapter by lazy {
        SellListInterestedAdapter {
            Toast.makeText(
                requireContext(),
                it.name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoldBinding.inflate(inflater, container, false)
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
        binding.rvInterested.adapter = interestedAdapter
        binding.rvInterested.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInterested.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        interestedAdapter.submitList(setDummyProducts())
    }
}