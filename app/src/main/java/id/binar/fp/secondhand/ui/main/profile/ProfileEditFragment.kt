package id.binar.fp.secondhand.ui.main.profile

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentProfileEditBinding
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.ImageBottomSheet
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import java.io.File

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private var getFile: File? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileEditBinding
        get() = FragmentProfileEditBinding::inflate

    override val isNavigationVisible: Boolean = false

    override fun setup() {
        super.setup()
        observeUser()
        chooseImage()
        editProfile()
    }

    override fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Lengkapi Info Akun"
        binding.toolbar.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    binding.ivProfile.loadImage(result.data.imageUrl)
                    binding.etName.setText(result.data.fullName)
                    binding.etPhone.setText(result.data.phoneNumber.toString())
                    binding.etCity.setText(result.data.city)
                    binding.etAddress.setText(result.data.address)
                }
                is Result.Error -> {
                    Helper.showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun chooseImage() {
        binding.ivProfile.setOnClickListener { checkPermissions(::initBottomSheet) }
    }

    private fun editProfile() {
//        if (arguments != null) {
//            val user = arguments?.getParcelable<UserDto>("user") as UserDto
//
//            binding.ivProfile.loadImage(user.imageUrl)
//            binding.etName.setText(user.fullName)
//            binding.etPhone.setText(user.phoneNumber.toString())
//            binding.etCity.setText(user.city)
//            binding.etAddress.setText(user.address)
//        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            val city = binding.etCity.text.toString()
            val address = binding.etAddress.text.toString()

            if (getFile != null) {
                val file = getFile as File
                updateProfile(name, phoneNumber, city, address, file)
            } else {
                updateProfile(name, phoneNumber, city, address)
            }
        }
    }

    private fun updateProfile(
        fullName: String,
        phoneNumber: String,
        city: String,
        address: String,
        image: File? = null
    ) {
        profileViewModel.editProfile(fullName, phoneNumber, city, address, image)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        Helper.showToast(requireContext(), "Profile berhasil diperbarui")
                        parentFragmentManager.popBackStack()
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Helper.showToast(requireContext(), result.error)
                    }
                }
            }
    }

    private fun initBottomSheet() {
        val bottomSheet = ImageBottomSheet()
        bottomSheet.show(childFragmentManager, ImageBottomSheet.TAG)
        bottomSheet.bottomSheetCallback = object : ImageBottomSheet.BottomSheetCallback {
            override fun onSelectImage(bitmap: Bitmap, file: File) {
                binding.ivProfile.loadImage(bitmap)
                getFile = file
                bottomSheet.dismiss()
            }

            override fun onSelectImage(uri: Uri, file: File) {
                binding.ivProfile.loadImage(uri)
                getFile = file
                bottomSheet.dismiss()
            }
        }
    }
}