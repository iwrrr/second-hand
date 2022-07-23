package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetStatusBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet
import id.binar.fp.secondhand.util.Status

class StatusBottomSheet : BaseBottomSheet<BottomSheetStatusBinding>() {

    var bottomSheetCallback: BottomSheetCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetStatusBinding
        get() = BottomSheetStatusBinding::inflate

    override fun setup() {
        val productId = arguments?.getInt("product_id") as Int
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.btnSetStatus.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
            binding.btnSetStatus.isEnabled = true

            binding.btnSetStatus.setOnClickListener {
                val radio = group.findViewById<RadioButton>(checkedId)
                var status = ""
                when (radio.id) {
                    R.id.radio_success -> {
                        status = Status.SUCCESS
                    }
                    R.id.radio_cancel -> {
                        status = Status.CANCEL
                    }
                }
                bottomSheetCallback?.onStatusUpdate(productId, status)
            }
        }
    }

    interface BottomSheetCallback {
        fun onStatusUpdate(productId: Int, status: String)
    }

    companion object {
        const val TAG = "StatusBottomSheet"
    }
}