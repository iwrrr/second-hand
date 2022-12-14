package id.binar.fp.secondhand.ui.main.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentSearchBinding
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.home.SearchAdapter
import id.binar.fp.secondhand.ui.main.product.DetailProductFragment
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(::onProductClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupSearch()
    }

    private fun setupSearch() {
        binding.toolbar.etSearch.requestFocus()
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.toolbar.etSearch, 0)

        binding.toolbar.etSearch.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch { viewModel.queryChannel.send(text.toString()) }
        }

        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.placeholder.root.isVisible = true
        observeProduct()
    }

    private fun observeProduct() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                    binding.placeholder.root.isVisible = false
                }
                is Result.Success -> {
                    hideShimmer()
                    if (result.data != null) {
                        binding.placeholder.root.isVisible = false
                        val availableProduct =
                            result.data.filter { it.status == Status.AVAILABLE }
                        searchAdapter.submitList(availableProduct)
                    }
                }
                is Result.Error -> {
                    hideShimmer()
                    binding.placeholder.root.isVisible = true
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onProductClicked(product: Product) {
        val fragment = DetailProductFragment()
        val bundle = Bundle()
        bundle.putInt("id", product.id)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            add(R.id.main_nav_host, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showShimmer() {
        binding.apply {
            shimmer.root.isInvisible = false
            shimmer.root.startShimmer()
            rvSearch.isInvisible = true
        }
    }

    private fun hideShimmer() {
        binding.apply {
            shimmer.root.isInvisible = true
            shimmer.root.stopShimmer()
            rvSearch.isInvisible = false
        }
    }
}