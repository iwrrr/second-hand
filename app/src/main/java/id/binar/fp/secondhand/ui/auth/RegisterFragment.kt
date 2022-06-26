package id.binar.fp.secondhand.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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
//            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun backToLogin() {
        binding.tvLogin.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}