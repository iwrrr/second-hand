package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetStatusBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet

class StatusBottomSheet : BaseBottomSheet<BottomSheetStatusBinding>() {

    var bottomSheetCallback: BottomSheetCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetStatusBinding
        get() = BottomSheetStatusBinding::inflate

    override fun setup() {
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

    interface BottomSheetCallback {
        fun onStatusUpdate(status: String)
    }

    companion object {
        const val TAG = "StatusBottomSheet"
    }
}