package id.binar.fp.secondhand.ui.main.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dropDownMenu()

        binding.contentToolbar.toolbarTitle.text = "Lengkapi Detail Produk"
        binding.contentToolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun dropDownMenu() {
        val category = arrayOf("Hobi", "Kendaraan", "Aksesoris")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_profile_city, category)
        binding.etProductCategory.setAdapter(arrayAdapter)
    }

}