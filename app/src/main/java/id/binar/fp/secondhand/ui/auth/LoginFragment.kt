package id.binar.fp.secondhand.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//            requireActivity().finish()
            requireActivity().onBackPressed()
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
}