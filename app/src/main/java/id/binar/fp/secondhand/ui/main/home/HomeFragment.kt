package id.binar.fp.secondhand.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.binar.fp.secondhand.databinding.FragmentHomeBinding
import id.binar.fp.secondhand.ui.main.adapter.home.CategoryAdapter
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListProductAdapter
import id.binar.fp.secondhand.util.dummy.DataDummy.setCategories
import id.binar.fp.secondhand.util.dummy.DataDummy.setDummyProducts

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter by lazy {
        CategoryAdapter {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }
    }

    private val productAdapter by lazy {
        SellListProductAdapter {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.etSearch.setOnClickListener {
            Toast.makeText(requireContext(), "tes", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        categoryAdapter.submitList(setCategories())

        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        productAdapter.submitList(setDummyProducts())
    }
}