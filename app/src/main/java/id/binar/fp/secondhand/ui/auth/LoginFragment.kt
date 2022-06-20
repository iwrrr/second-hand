package id.binar.fp.secondhand.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnMasuk()
        toRegisFrag()
    }

    private fun btnMasuk(){
        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    private fun toRegisFrag(){
        binding.tvDaftarDisini.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}