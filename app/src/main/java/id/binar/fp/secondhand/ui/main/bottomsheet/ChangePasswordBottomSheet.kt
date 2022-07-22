package id.binar.fp.secondhand.ui.main.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import id.binar.fp.secondhand.databinding.BottomSheetChangePasswordBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet

class ChangePasswordBottomSheet : BaseBottomSheet<BottomSheetChangePasswordBinding>() {

    lateinit var title: String
    lateinit var hint: String

    var changePasswordCallback: ChangePasswordCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetChangePasswordBinding
        get() = BottomSheetChangePasswordBinding::inflate

    override fun setup() {
        setupEditText()
    }

    private fun setupEditText() {
        binding.tvBottomSheetTitle.text = title

        binding.etCurrPassword.doOnTextChanged { text, _, _, count ->
            if (text.isNullOrBlank()) {
                binding.tlCurrPassword.error = "Wajib diisi."
            }
            if (count > 0) {
                binding.tlCurrPassword.isErrorEnabled = false
            }
        }

        binding.etNewPassword.doOnTextChanged { text, _, _, count ->
            if (text.isNullOrBlank()) {
                binding.tlNewPassword.error = "Wajib diisi."
            }
            if (count > 0) {
                binding.tlNewPassword.isErrorEnabled = false
            }
        }

        binding.etConfirmPassword.doOnTextChanged { text, _, _, count ->
            if (text.isNullOrBlank()) {
                binding.tlConfirmPassword.error = "Wajib diisi."
            }
            if (count > 0) {
                binding.tlConfirmPassword.isErrorEnabled = false
            }
        }

        updateProfile()
    }

    private fun updateProfile() {
        binding.btnSave.setOnClickListener {
            val currPassword = binding.etCurrPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (currPassword.isBlank()) {
                binding.tlCurrPassword.error = "Wajib diisi."
                return@setOnClickListener
            }

            if (newPassword.isBlank()) {
                binding.tlNewPassword.error = "Wajib diisi."
                return@setOnClickListener
            }

            if (confirmPassword.isBlank()) {
                binding.tlConfirmPassword.error = "Wajib diisi."
                return@setOnClickListener
            }

            changePasswordCallback?.onUpdate(currPassword, newPassword, confirmPassword)
        }
    }

    interface ChangePasswordCallback {
        fun onUpdate(currPassword: String, newPassword: String, confirmPassword: String)
    }
}