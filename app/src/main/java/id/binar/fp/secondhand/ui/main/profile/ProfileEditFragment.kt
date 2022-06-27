package id.binar.fp.secondhand.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentProfileEditBinding


class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false
        dropDownMenu()

        binding.contentToolbar.toolbarTitle.text = "Lengkapi Info Akun"
        binding.contentToolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun dropDownMenu() {
//        val city = arrayOf("Jakarta", "Bandung", "Surabaya", "Semarang", "Yogyakarta")
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_profile_city, city)
//        binding.etCity.setAdapter(arrayAdapter)
    }
}