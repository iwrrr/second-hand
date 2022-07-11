package id.binar.fp.secondhand.ui.main.seller.interested

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentBidderInfoBinding
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.ContactBottomSheet
import id.binar.fp.secondhand.ui.main.bottomsheet.StatusBottomSheet

@AndroidEntryPoint
class BidderInfoFragment : BaseFragment<FragmentBidderInfoBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBidderInfoBinding
        get() = FragmentBidderInfoBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupBottomSheet()
    }

    override fun setupToolbar() {
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
        val bottomSheet = ContactBottomSheet()
        bottomSheet.show(childFragmentManager, ContactBottomSheet.TAG)
    }

    private fun showBottomSheetStatus() {
        val bottomSheet = StatusBottomSheet()
        bottomSheet.show(childFragmentManager, StatusBottomSheet.TAG)

        bottomSheet.bottomSheetCallback = object : StatusBottomSheet.BottomSheetCallback {
            override fun onStatusUpdate(status: String) {
                // TODO: Update status
                bottomSheet.dismiss()
            }
        }
    }
}