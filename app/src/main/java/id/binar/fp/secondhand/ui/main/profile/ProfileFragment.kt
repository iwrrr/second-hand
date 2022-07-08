package id.binar.fp.secondhand.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.databinding.FragmentProfileBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.main.MainActivity
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var user: UserDto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUser()
        onEditClicked()
        onLoginClicked()
        onLogoutClicked()
    }

    override fun onResume() {
        super.onResume()
        checkAuth()
        observeUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.menu.logout.isVisible = true
                binding.btnLogin.isVisible = false
            }
        }
    }

    fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
//                    user = result.data
                    binding.ivProfile.loadImage(result.data.imageUrl)
                }
                is Result.Error -> {
//                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
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