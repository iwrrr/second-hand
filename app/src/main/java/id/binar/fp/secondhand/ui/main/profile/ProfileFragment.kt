package id.binar.fp.secondhand.ui.main.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
import id.binar.fp.secondhand.ui.main.seller.SellerFragment
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
        setupSwipeLayout()

        onEditClicked()
        onSellerClicked()
        onLoginClicked()
        onLogoutClicked()
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                with(binding) {
                    tvName.isVisible = true
                    tvPhone.isVisible = true
                    tvEmail.isVisible = true
                    ivEdit.isVisible = true
                    menu.seller.isVisible = true
                    menu.logout.isVisible = true
                    btnLogin.isVisible = false
                }
                observeUser()
            }
            setConstraint(!token.isNullOrBlank())
        }
    }

    private fun setupSwipeLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            observeUser()
        }
    }

    private fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
//                    user = result.data
                    with(binding) {
                        ivProfile.loadImage(result.data.imageUrl)
                        tvName.text = result.data.fullName
                        tvPhone.text = result.data.phoneNumber
                        tvEmail.text = result.data.email
                        swipeRefresh.isRefreshing = false
                    }
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun onEditClicked() {
        binding.ivEdit.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, ProfileEditFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun onSellerClicked() {
        binding.menu.seller.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, SellerFragment())
                addToBackStack(null)
                commit()
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

    private fun setConstraint(isLogin: Boolean) {
        val constraintLayout: ConstraintLayout = binding.constraintLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        if (isLogin) {
            constraintSet.clear(
                binding.ivProfile.id,
                ConstraintSet.END
            )
        } else {
            constraintSet.connect(
                binding.ivProfile.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                0
            )
        }

        constraintSet.applyTo(constraintLayout)
    }
}