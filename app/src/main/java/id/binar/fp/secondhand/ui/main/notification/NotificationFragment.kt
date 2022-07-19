package id.binar.fp.secondhand.ui.main.notification

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentNotificationBinding
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.NotificationAdapter
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val notificationViewModel: NotificationViewModel by viewModels()

    private val notificationAdapter by lazy { NotificationAdapter(::onNotificationClicked) }

    private var notifications = emptyList<Notification>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationBinding
        get() = FragmentNotificationBinding::inflate

    override fun setup() {
        super.setup()
        onLoginClicked()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.content.rvNotification.adapter = notificationAdapter
        binding.content.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        observeNotification()
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeNotification() }
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

    private fun observeNotification() {
        notificationViewModel.getNotification().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }
                is Result.Success -> {
                    hideShimmer()
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        if (result.data.isNotEmpty()) {
                            notificationAdapter.submitList(result.data)
                            notifications = result.data
                        } else {
                            binding.empty.root.isVisible = true
                            binding.content.root.isVisible = false
                        }
                    }
                }
                is Result.Error -> {
                    hideShimmer()
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun observeUpdateNotification(notification: Notification) {
        notificationViewModel.updateNotification(notification.id, notification)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {}
                    is Result.Error -> {
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

    private fun onNotificationClicked(notification: Notification) {
        if (!notification.read) {
            observeUpdateNotification(notification)
        }
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