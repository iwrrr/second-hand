package id.binar.fp.secondhand.ui.main.sell.interested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetContactBinding
import id.binar.fp.secondhand.databinding.BottomSheetStatusBinding
import id.binar.fp.secondhand.databinding.FragmentBidderInfoBinding

@AndroidEntryPoint
class BidderInfoFragment : Fragment() {

    private var _binding: FragmentBidderInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBidderInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false

        setupToolbar()
        setupBottomSheet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Info Penawar"
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setupBottomSheet() {
        binding.contentBtnNotAcceptedYet.btnAccept.setOnClickListener {
            showBottomSheetContact()
            binding.contentBtnNotAcceptedYet.root.isInvisible = true
            binding.contentBtnAccepted.root.isInvisible = false
        }

        binding.contentBtnAccepted.btnContact.setOnClickListener {
            showBottomSheetContact()
        }

        binding.contentBtnAccepted.btnStatus.setOnClickListener {
            showBottomSheetStatus()
        }
    }

    private fun showBottomSheetContact() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetContactBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()
    }

    private fun showBottomSheetStatus() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetStatusBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.radioGroup.setOnCheckedChangeListener { _, _ ->
            bottomSheetBinding.btnSetStatus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
            bottomSheetBinding.btnSetStatus.isEnabled = true
        }
    }
}