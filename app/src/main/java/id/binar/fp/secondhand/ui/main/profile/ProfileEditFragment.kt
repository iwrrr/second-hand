package id.binar.fp.secondhand.ui.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetChooseImageBinding
import id.binar.fp.secondhand.databinding.FragmentProfileEditBinding
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.createTempFile
import id.binar.fp.secondhand.util.uriToFile
import java.io.File

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private lateinit var currentPhotoPath: String

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var getFile: File? = null

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val file = File(currentPhotoPath)
                val bitmapImage = BitmapFactory.decodeFile(file.path)
                getFile = file
                binding.ivProfile.loadImage(bitmapImage)
            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val uriImage: Uri = result.data?.data as Uri
                val file = uriToFile(uriImage, requireContext())
                getFile = file
                binding.ivProfile.loadImage(uriImage)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false

        binding.toolbar.toolbarTitle.text = "Lengkapi Info Akun"
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }

        binding.ivProfile.setOnClickListener {
            checkPermissions()
        }

        observeUser()
        editProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomSheetChooseImageBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.btnCamera.setOnClickListener {
            openCamera()
            bottomSheet.hide()
        }

        bottomSheetBinding.btnGallery.setOnClickListener {
            openGallery()
            bottomSheet.hide()
        }
    }

    private fun checkPermissions() {
        if (checkIfPermissionsIsGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        ) {
            setupBottomSheet()
        }
    }

    private fun checkIfPermissionsIsGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, please allow permissions from App Settings.")
            .setPositiveButton("App Settings") { _, _ -> openAppSettings() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent()
        val uri = Uri.fromParts("package", requireActivity().packageName, null)

        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = uri

        startActivity(intent)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherGallery.launch(chooser)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createTempFile(requireContext()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "id.binar.fp.secondhand",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherCamera.launch(intent)
        }
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
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                        Toast.makeText(
                            requireContext(),
                            "Profile berhasil diperbarui",
                            Toast.LENGTH_SHORT
                        ).show()
                        parentFragmentManager.popBackStack()
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
    }
}