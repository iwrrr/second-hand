package id.binar.fp.secondhand.ui.main.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentHistoryBinding
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.HistoryAdapter
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    private val historyAdapter by lazy { HistoryAdapter(requireContext(), ::onHistoryClicked) }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    override fun setup() {
        super.setup()
        onLoginClicked()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.content.rvHistory.adapter = historyAdapter
        binding.content.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        observeHistory()
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeHistory() }
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.auth.root.isVisible = false
                binding.shimmer.root.isVisible = false
                setupRecyclerView()
            } else {
                binding.content.root.isVisible = false
                binding.auth.root.isVisible = true
                binding.shimmer.root.isVisible = false
            }
        }
    }

    private fun observeHistory() {
        historyViewModel.getHistory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }
                is Result.Success -> {
                    hideShimmer()
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        if (result.data.isNotEmpty()) {
                            historyAdapter.submitList(result.data)
                        } else {
                            binding.empty.root.isVisible = true
                            binding.content.root.isVisible = false
                        }
                    }
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onLoginClicked() {
        binding.auth.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    private fun onHistoryClicked(history: History) {
        Helper.showToast(requireContext(), history.id.toString())
    }

    private fun showShimmer() {
        binding.apply {
            shimmer.root.isInvisible = false
            shimmer.root.startShimmer()
            content.root.isInvisible = true
        }
    }

    private fun hideShimmer() {
        binding.apply {
            shimmer.root.isInvisible = true
            shimmer.root.stopShimmer()
            content.root.isInvisible = false
        }
    }
}