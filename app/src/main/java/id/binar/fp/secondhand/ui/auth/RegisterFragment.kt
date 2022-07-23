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
import id.binar.fp.secondhand.databinding.FragmentRegisterBinding
import id.binar.fp.secondhand.util.Extensions.isValidated
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register()
        backToLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            val city = binding.etCity.text.toString()
            val address = binding.etAddress.text.toString()

            if (validateData(name, email, password, phoneNumber, city, address)) {
                observeRegister(name, email, password, phoneNumber, city, address)
            }
        }
    }

    private fun backToLogin() {
        binding.tvLogin.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observeRegister(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String
    ) {
        viewModel.register(name, email, password, phoneNumber, city, address)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.loading.root.isVisible = true
                        binding.btnRegister.isVisible = false
                    }
                    is Result.Success -> {
                        binding.loading.root.isVisible = false
                        binding.btnRegister.isVisible = true
                        requireActivity().finish()
                    }
                    is Result.Error -> {
                        binding.loading.root.isVisible = false
                        binding.btnRegister.isVisible = true
                        Helper.showToast(requireContext(), result.message.toString())
                    }
                }
            }
    }

    private fun validateData(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                binding.etlName.error = getString(R.string.text_name_required)
                binding.etlName.requestFocus()
                false
            }
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
            phoneNumber.isBlank() -> {
                binding.etlPhone.error = getString(R.string.text_phone_number_required)
                binding.etlPhone.requestFocus()
                false
            }
            city.isEmpty() -> {
                binding.etlCity.error = getString(R.string.text_city_required)
                binding.etlCity.requestFocus()
                false
            }
            address.isEmpty() -> {
                binding.etlAddress.error = getString(R.string.text_address_required)
                binding.etlAddress.requestFocus()
                false
            }
            else -> true
        }
    }
}