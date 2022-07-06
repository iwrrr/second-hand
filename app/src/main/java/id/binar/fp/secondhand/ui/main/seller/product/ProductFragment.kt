package id.binar.fp.secondhand.ui.main.seller.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentProductBinding
import id.binar.fp.secondhand.ui.main.adapter.sell.SellerProductAdapter
import id.binar.fp.secondhand.ui.main.seller.SellerViewModel
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val sellerViewModel: SellerViewModel by viewModels()

    private val productAdapter by lazy {
        SellerProductAdapter {
//            Toast.makeText(
//                requireContext(),
//                it.name,
//                Toast.LENGTH_SHORT
//            ).show()
//            requireParentFragment().parentFragmentManager.beginTransaction().apply {
//                add(R.id.main_nav_host, ProductDetailFragment())
//                addToBackStack(null)
//                commit()
//            }
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
        observeProduct()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.content.rvProduct.adapter = productAdapter
        binding.content.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeProduct() {
        sellerViewModel.getSellerProduct().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    val availableProduct = result.data?.filter { it.status == "available" }
                    if (availableProduct != null) {
                        productAdapter.submitList(availableProduct)
                    } else {
                        binding.content.root.isVisible = false
                        binding.empty.root.isVisible = true
                    }
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}