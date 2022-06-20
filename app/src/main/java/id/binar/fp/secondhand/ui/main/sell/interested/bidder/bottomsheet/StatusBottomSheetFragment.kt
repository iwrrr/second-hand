package id.binar.fp.secondhand.ui.main.sell.interested.bidder.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentStatusBottomSheetBinding

@AndroidEntryPoint
class StatusBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentStatusBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatusBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            binding.btnSetStatus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
            binding.btnSetStatus.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "StatusBottomSheetFragment"
    }
}