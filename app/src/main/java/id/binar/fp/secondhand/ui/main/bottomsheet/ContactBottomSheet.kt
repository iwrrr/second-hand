package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import id.binar.fp.secondhand.databinding.BottomSheetContactBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet

class ContactBottomSheet : BaseBottomSheet<BottomSheetContactBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetContactBinding
        get() = BottomSheetContactBinding::inflate

    companion object {
        const val TAG = "ContactBottomSheet"
    }
}