package id.binar.fp.secondhand.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentLoginBinding
import id.binar.fp.secondhand.util.Extensions.isValidated
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        register()

        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateData(email, password)) {
                observeLogin(email, password)
            }
        }
    }

    private fun register() {
        binding.tvRegister.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                add(R.id.auth_nav_host, RegisterFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeLogin(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.root.isVisible = true
                    binding.btnLogin.isVisible = false
                }
                is Result.Success -> {
                    binding.loading.root.isVisible = false
                    binding.btnLogin.isVisible = true
                    requireActivity().onBackPressed()
                }
                is Result.Error -> {
                    binding.loading.root.isVisible = false
                    binding.btnLogin.isVisible = true
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun validateData(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.etlEmail.error = getString(R.string.text_email_required)
                binding.etlEmail.requestFocus()
                false
            }
            !email.isValidated() -> {
                binding.etlEmail.error = getString(R.string.text_email_not_valid)
                binding.etlEmail.requestFocus()
                false
            }
            password.isEmpty() -> {
                binding.etlPassword.error = getString(R.string.text_password_required)
                binding.etlPassword.requestFocus()
                false
            }
            password.length < 6 -> {
                binding.etlPassword.error = getString(R.string.text_password_more_than_6_characters)
                binding.etlPassword.requestFocus()
                false
            }
            else -> true
        }
    }
}