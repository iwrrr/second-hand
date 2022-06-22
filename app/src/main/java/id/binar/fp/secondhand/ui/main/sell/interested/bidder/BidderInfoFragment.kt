package id.binar.fp.secondhand.ui.main.sell.interested.bidder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentBidderInfoBinding
import id.binar.fp.secondhand.ui.main.sell.interested.bidder.bottomsheet.ContactBottomSheetFragment
import id.binar.fp.secondhand.ui.main.sell.interested.bidder.bottomsheet.StatusBottomSheetFragment

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

        binding.contentToolbar.toolbarTitle.text = "Info Penawar"
        binding.contentToolbar.btnBack.setOnClickListener { findNavController().popBackStack() }

        val contact = ContactBottomSheetFragment()
        val status = StatusBottomSheetFragment()

        binding.contentBtnNotAcceptedYet.btnAccept.setOnClickListener {
            contact.show(childFragmentManager, ContactBottomSheetFragment.TAG)

            binding.contentBtnNotAcceptedYet.root.isInvisible = true
            binding.contentBtnAccepted.root.isInvisible = false
        }

        binding.contentBtnAccepted.btnContact.setOnClickListener {
            contact.show(childFragmentManager, ContactBottomSheetFragment.TAG)
        }

        binding.contentBtnAccepted.btnStatus.setOnClickListener {
            status.show(childFragmentManager, StatusBottomSheetFragment.TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}