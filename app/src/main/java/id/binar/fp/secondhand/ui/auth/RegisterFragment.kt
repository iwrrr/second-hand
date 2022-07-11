package id.binar.fp.secondhand.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentRegisterBinding
import id.binar.fp.secondhand.util.Extensions.isValidated
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
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
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
                binding.etlName.error = "Nama tidak boleh kosong"
                binding.etlName.requestFocus()
                false
            }
            email.isEmpty() -> {
                binding.etlEmail.error = "Email tidak boleh kosong"
                binding.etlEmail.requestFocus()
                false
            }
            !email.isValidated() -> {
                binding.etlEmail.error = "Email tidak valid"
                binding.etlEmail.requestFocus()
                false
            }
            password.isEmpty() -> {
                binding.etlPassword.error = "Password tidak boleh kosong"
                binding.etlPassword.requestFocus()
                false
            }
            password.length < 6 -> {
                binding.etlPassword.error = "Password harus lebih dari 6 karakter"
                binding.etlPassword.requestFocus()
                false
            }
            phoneNumber.isBlank() -> {
                binding.etlPhone.error = "Nomor telepon tidak boleh kosong"
                binding.etlPhone.requestFocus()
                false
            }
            city.isEmpty() -> {
                binding.etlCity.error = "Kota tidak boleh kosong"
                binding.etlCity.requestFocus()
                false
            }
            address.isEmpty() -> {
                binding.etlAddress.error = "Alamat tidak boleh kosong"
                binding.etlAddress.requestFocus()
                false
            }
            else -> true
        }
    }
}