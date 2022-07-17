package id.binar.fp.secondhand.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentHomeBinding
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.home.CategoryAdapter
import id.binar.fp.secondhand.ui.main.adapter.home.ProductAdapter
import id.binar.fp.secondhand.ui.main.product.DetailProductFragment
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val categoryAdapter by lazy { CategoryAdapter(::onCategoryClicked) }
    private val productAdapter by lazy { ProductAdapter(::onProductClicked) }

    private var products = emptyList<Product>()
    private var categories = emptyList<Category>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        super.setup()
        setupSearch()
        setupBanner()
        setupRecyclerView()
        onRefreshProduct()
    }

    private fun setupSearch() {
        binding.etSearch.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, SearchFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setupBanner() {
        observeBanner()
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

    private fun observeBanner() {
        viewModel.getBanner().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        binding.ivBanner.setImageList(result.data.map { it.imageUrl })
                    }
                }
                is Result.Error -> {}
            }
        }
    }

    private fun observeCategory() {
        viewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        val allCategory = result.data.toMutableList()
                        allCategory.add(0, Category(id = 0, name = "Semua"))
                        categoryAdapter.submitList(allCategory as List<Category>)
                        categories = allCategory
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
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
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        val availableProduct =
                            result.data.filter { it.status == Status.AVAILABLE }
                        productAdapter.submitList(availableProduct)
                        products = availableProduct
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onCategoryClicked(category: Category) {
        if (category.id != 0) {
            val filteredProducts = products.filter { data ->
                data.categories?.map { it.id }?.contains(category.id) == true
            }
            productAdapter.submitList(filteredProducts)
        } else {
            productAdapter.submitList(products)
        }
    }

    private fun onProductClicked(product: Product) {
        val detailFragment = DetailProductFragment()
        val bundle = Bundle()
        bundle.putInt("id", product.id)
        detailFragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            add(R.id.main_nav_host, detailFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun onRefreshProduct() {
        binding.swipeRefresh.setOnRefreshListener { observeListProduct() }
    }
}