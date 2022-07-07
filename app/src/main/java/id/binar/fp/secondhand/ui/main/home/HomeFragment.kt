package id.binar.fp.secondhand.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.FragmentHomeBinding
import id.binar.fp.secondhand.ui.main.adapter.home.CategoryAdapter
import id.binar.fp.secondhand.ui.main.adapter.home.ProductAdapter
import id.binar.fp.secondhand.ui.main.product.ProductDetailFragment
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter(::onCategoryClicked)
    }

    private val productAdapter by lazy {
        ProductAdapter(::onProductClicked)
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
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, SearchFragment())
                addToBackStack(null)
                commit()
            }
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
        observeCategory()

        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
        observeListProduct()
    }

    private fun observeCategory() {
        viewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    categoryAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }
    }

    private fun observeListProduct() {
        viewModel.getAllProduct().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
//                    val availableProduct = result.data.filter { it.status == "available" }
                    productAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onCategoryClicked(category: CategoryDto) {
        Toast.makeText(requireContext(), category.name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductClicked(product: ProductDto) {
        val fragment = ProductDetailFragment()
        val bundle = Bundle()
        bundle.putInt("id", product.id)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            add(R.id.main_nav_host, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun filterProduct(products: List<ProductDto>, category: CategoryDto): List<ProductDto> {
        val filteredList = arrayListOf<ProductDto>()
        for (product in products) {
            if (product.categories?.contains(category) == true) {
                filteredList.add(product)
            }
        }
        return filteredList
    }
}