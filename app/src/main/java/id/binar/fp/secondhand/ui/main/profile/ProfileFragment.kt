package id.binar.fp.secondhand.ui.main.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.databinding.FragmentProfileBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.MainActivity
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var user: UserDto

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun setup() {
        super.setup()
        onEditClicked()
        onLoginClicked()
        onLogoutClicked()
        setupSwipeLayout()
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.menu.logout.isVisible = true
                binding.btnLogin.isVisible = false
                observeUser()
            }
        }
    }

    private fun setupSwipeLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            observeUser()
        }
    }

    fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
//                    user = result.data
                    binding.ivProfile.loadImage(result.data.imageUrl)
                    binding.swipeRefresh.isRefreshing = false
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun onEditClicked() {
        binding.menu.edit.setOnClickListener {
            authViewModel.getToken().observe(viewLifecycleOwner) { token ->
                if (!token.isNullOrBlank()) {
                    parentFragmentManager.beginTransaction().apply {
                        add(R.id.main_nav_host, ProfileEditFragment())
                        addToBackStack(null)
                        commit()
                    }
                } else {
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                }
            }
        }
    }

    private fun onLoginClicked() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    private fun onLogoutClicked() {
        binding.menu.logout.setOnClickListener {
            authViewModel.logout().observe(viewLifecycleOwner) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}