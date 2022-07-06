package id.binar.fp.secondhand.ui.main.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.FragmentSearchBinding
import id.binar.fp.secondhand.ui.main.adapter.home.SearchAdapter
import id.binar.fp.secondhand.ui.main.product.ProductDetailFragment
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(::onProductClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false

        setupSearch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearch() {
        binding.etSearch.requestFocus()

        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSearch, 0)

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch { viewModel.queryChannel.send(text.toString()) }
        }

        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())

        binding.placeholder.root.isVisible = true

        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                    binding.rvSearch.isVisible = false
                    binding.placeholder.root.isVisible = false
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    binding.rvSearch.isVisible = true
                    binding.placeholder.root.isVisible = false
//                    val productAvailable = result.data.filter { it.status == "available" }
                    searchAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    binding.rvSearch.isVisible = false
                    binding.placeholder.root.isVisible = false
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
}