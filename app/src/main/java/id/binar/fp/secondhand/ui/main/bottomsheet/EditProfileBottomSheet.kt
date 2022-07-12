package id.binar.fp.secondhand.ui.main.bottomsheet

import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import id.binar.fp.secondhand.databinding.BottomSheetEditProfileBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet
import id.binar.fp.secondhand.util.Constants

class EditProfileBottomSheet : BaseBottomSheet<BottomSheetEditProfileBinding>() {

    lateinit var title: String
    lateinit var hint: String

    var updateProfileCallback: UpdateProfileCallback? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetEditProfileBinding
        get() = BottomSheetEditProfileBinding::inflate

    override fun setup() {
        setupEditText()
    }

    private fun setupEditText() {
        val data = arguments?.getString("data")

        binding.tvBottomSheetTitle.text = title

        when (hint) {
            Constants.HINT_NAME -> {
                binding.textLayout.hint = hint
                binding.editText.setText(data)
                binding.editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                binding.editText.filters = arrayOf(InputFilter.LengthFilter(50))
            }
            Constants.HINT_PHONE -> {
                binding.textLayout.hint = hint
                binding.editText.setText(data)
                binding.editText.inputType = InputType.TYPE_CLASS_PHONE
                binding.editText.filters = arrayOf(InputFilter.LengthFilter(14))
            }
            Constants.HINT_CITY -> {
                binding.textLayout.hint = hint
                binding.editText.setText(data)
                binding.editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                binding.editText.filters = arrayOf(InputFilter.LengthFilter(50))
            }
            Constants.HINT_ADDRESS -> {
                binding.textLayout.hint = hint
                binding.editText.setText(data)
                binding.editText.filters = arrayOf(InputFilter.LengthFilter(100))
                binding.editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                binding.editText.isSingleLine = false
                binding.editText.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                binding.editText.gravity = Gravity.START or Gravity.TOP
                binding.editText.minLines = 3
            }
        }

        binding.editText.doOnTextChanged { text, _, _, count ->
            if (text.isNullOrBlank()) {
                binding.textLayout.error = "Wajib diisi."
            }
            if (count > 0) {
                binding.textLayout.isErrorEnabled = false
            }
        }

        updateProfile()
    }

    private fun updateProfile() {
        binding.btnSave.setOnClickListener {
            val value = binding.editText.text.toString()
            if (value.isBlank()) {
                binding.textLayout.error = "Wajib diisi."
                return@setOnClickListener
            }
            updateProfileCallback?.onUpdate(value)
        }
    }

    interface UpdateProfileCallback {
        fun onUpdate(data: String)
    }
}