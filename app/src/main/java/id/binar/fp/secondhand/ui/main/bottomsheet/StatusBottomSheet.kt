package id.binar.fp.secondhand.ui.main.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetStatusBinding

class StatusBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetStatusBinding? = null
    private val binding get() = _binding!!

    var bottomSheetCallback: BottomSheetCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.btnSetStatus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
            binding.btnSetStatus.isEnabled = true

            binding.btnSetStatus.setOnClickListener {
                val radio = group.findViewById<RadioButton>(checkedId)
                var status = ""
                when (radio.id) {
                    R.id.radio_success -> {
                        status = "success"
                    }
                    R.id.radio_cancel -> {
                        status = "cancel"
                    }
                }
                bottomSheetCallback?.onStatusUpdate(status)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface BottomSheetCallback {
        fun onStatusUpdate(status: String)
    }

    companion object {
        const val TAG = "StatusBottomSheet"
    }
}