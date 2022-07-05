package id.binar.fp.secondhand.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentHomeBinding
import id.binar.fp.secondhand.ui.main.adapter.home.CategoryAdapter
import id.binar.fp.secondhand.ui.main.adapter.home.ProductAdapter
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListProductAdapter
import id.binar.fp.secondhand.ui.main.product.ProductDetailFragment
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.UserPreferences
import id.binar.fp.secondhand.util.dummy.DataDummy.setDummyProducts
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
        CategoryAdapter {
            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
        }
    }

    private val productAdapter by lazy {
        ProductAdapter { result->
            val bundle = Bundle()
            bundle.putInt("id",result.id!!)
            val fragment = ProductDetailFragment()
            fragment.arguments = bundle
            Toast.makeText(requireContext(), result.name.toString(), Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, fragment)
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
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
                    result.data.let { response ->
                        categoryAdapter.submitList(response)
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }
    }


    private fun observeListProduct(){
        viewModel.getAllProduct().observe(viewLifecycleOwner){result->
            when(result){
                is Result.Loading->{

                }
                is Result.Success->{
                    result.data.let { response->
                        productAdapter.submitList(response)
                    }
                }
                is Result.Error->{
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}