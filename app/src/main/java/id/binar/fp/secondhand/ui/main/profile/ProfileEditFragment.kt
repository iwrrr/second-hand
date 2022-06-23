package id.binar.fp.secondhand.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        dropDownMenu()

        binding.contentToolbar.toolbarTitle.text = "Lengkapi Info Akun"
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