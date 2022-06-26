package id.binar.fp.secondhand.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentProfileBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
        binding.menu.edit.setOnClickListener {
//            findNavController().navigate(R.id.profileEditFragment)
//            startActivity(Intent(requireContext(), AuthActivity::class.java))
            parentFragmentManager.beginTransaction().apply {
                add(R.id.main_nav_host, ProfileEditFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.menu.logout.isVisible = false
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST", "onStart: Test")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST", "onResume: Test")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST", "onPause: Test")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST", "onStop: Test")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}