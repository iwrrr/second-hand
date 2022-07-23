package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.databinding.BottomSheetDeleteBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet

class DeleteBottomSheet : BaseBottomSheet<BottomSheetDeleteBinding>() {

    var bottomSheetCallback: BottomSheetCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetDeleteBinding
        get() = BottomSheetDeleteBinding::inflate

    override fun setup() {
        binding.delete.setOnClickListener { bottomSheetCallback?.onDelete() }
        binding.cancel.setOnClickListener { dismiss() }
    }

    interface BottomSheetCallback {
        fun onDelete()
    }

    companion object {
        const val TAG = "DeleteBottomSheet"
    }
}