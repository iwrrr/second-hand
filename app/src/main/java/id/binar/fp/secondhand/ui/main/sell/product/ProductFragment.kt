package id.binar.fp.secondhand.ui.main.sell.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentProductBinding
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListProductAdapter
import id.binar.fp.secondhand.ui.main.product.ProductDetailFragment

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val productAdapter by lazy {
        SellListProductAdapter {
            Toast.makeText(
                requireContext(),
                it.name,
                Toast.LENGTH_SHORT
            ).show()
            requireParentFragment().parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, ProductDetailFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
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

        binding.content.rvProduct.adapter = productAdapter
        binding.content.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)

//        productAdapter.submitList(setDummyProducts())
    }
}