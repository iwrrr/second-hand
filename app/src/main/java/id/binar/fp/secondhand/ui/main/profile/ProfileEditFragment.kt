package id.binar.fp.secondhand.ui.main.profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentProfileEditBinding
import id.binar.fp.secondhand.domain.model.User
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.EditProfileBottomSheet
import id.binar.fp.secondhand.ui.main.bottomsheet.ImageBottomSheet
import id.binar.fp.secondhand.util.Constants
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

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupRefresh()
        observeUser()
        onChooseImage()
    }

    override fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Lengkapi Info Akun"
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener { observeUser() }
    }

    private fun setupImageBottomSheet() {
        val bottomSheet = ImageBottomSheet()
        bottomSheet.show(childFragmentManager, ImageBottomSheet.TAG)
        bottomSheet.bottomSheetCallback = object : ImageBottomSheet.BottomSheetCallback {
            override fun onSelectImage(bitmap: Bitmap, file: File) {
                binding.ivProfile.loadImage(bitmap)
                getFile = file
                updateProfile(image = getFile)
                bottomSheet.dismiss()
            }

            override fun onSelectImage(uri: Uri, file: File) {
                binding.ivProfile.loadImage(uri)
                updateProfile(image = file)
                bottomSheet.dismiss()
            }
        }
    }

    private fun setupEditBottomSheet(title: String, hint: String, data: String?) {
        val editProfileBottomSheet = EditProfileBottomSheet()
        val bundle = Bundle()

        bundle.putString("data", data)
        editProfileBottomSheet.arguments = bundle
        editProfileBottomSheet.title = title
        editProfileBottomSheet.hint = hint
        editProfileBottomSheet.show(childFragmentManager, null)

        when (hint) {
            Constants.HINT_NAME -> {
                editProfileBottomSheet.updateProfileCallback =
                    object : EditProfileBottomSheet.UpdateProfileCallback {
                        override fun onUpdate(data: String) {
                            updateProfile(fullName = data)
                            editProfileBottomSheet.dismiss()
                        }
                    }
            }
            Constants.HINT_PHONE -> {
                editProfileBottomSheet.updateProfileCallback =
                    object : EditProfileBottomSheet.UpdateProfileCallback {
                        override fun onUpdate(data: String) {
                            updateProfile(phoneNumber = data)
                            editProfileBottomSheet.dismiss()
                        }
                    }
            }
            Constants.HINT_CITY -> {
                editProfileBottomSheet.updateProfileCallback =
                    object : EditProfileBottomSheet.UpdateProfileCallback {
                        override fun onUpdate(data: String) {
                            updateProfile(city = data)
                            editProfileBottomSheet.dismiss()
                        }
                    }
            }
            Constants.HINT_ADDRESS -> {
                editProfileBottomSheet.updateProfileCallback =
                    object : EditProfileBottomSheet.UpdateProfileCallback {
                        override fun onUpdate(data: String) {
                            updateProfile(address = data)
                            editProfileBottomSheet.dismiss()
                        }
                    }
            }
        }
    }

    private fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data != null) {
                        binding.swipeRefresh.isRefreshing = false
                        binding.ivProfile.loadImage(result.data.imageUrl)
                        binding.tvProfileName.data.text = result.data.fullName
                        binding.tvProfilePhone.data.text = result.data.phoneNumber.toString()
                        binding.tvProfileCity.data.text = result.data.city
                        binding.tvProfileAddress.text = result.data.address
                        onDataClicked(result.data)
                    }
                }
                is Result.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun updateProfile(
        fullName: String? = null,
        phoneNumber: String? = null,
        city: String? = null,
        address: String? = null,
        image: File? = null
    ) {
        profileViewModel.updateProfile(fullName, phoneNumber, city, address, image)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        Helper.showToast(requireContext(), "Profile berhasil diperbarui")
                        observeUser()
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Helper.showToast(requireContext(), result.message.toString())
                    }
                }
            }
    }

    private fun onChooseImage() {
        binding.ivProfile.setOnClickListener { checkPermissions(::setupImageBottomSheet) }
    }

    private fun onDataClicked(user: User) {
        binding.tvProfileName.root.setOnClickListener {
            setupEditBottomSheet(Constants.TITLE_NAME, Constants.HINT_NAME, user.fullName)
        }

        binding.tvProfilePhone.root.setOnClickListener {
            setupEditBottomSheet(Constants.TITLE_PHONE, Constants.HINT_PHONE, user.phoneNumber)
        }

        binding.tvProfileCity.root.setOnClickListener {
            setupEditBottomSheet(Constants.TITLE_CITY, Constants.HINT_CITY, user.city)
        }

        binding.tvProfileAddress.setOnClickListener {
            setupEditBottomSheet(Constants.TITLE_ADDRESS, Constants.HINT_ADDRESS, user.address)
        }
    }
}