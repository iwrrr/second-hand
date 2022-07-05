package id.binar.fp.secondhand.ui.main.product

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.BottomSheetChooseImageBinding
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                binding.content.ivProductImage.loadImage(result)
            }
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                binding.content.ivProductImage.loadImage(bitmap)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAuth()

        onLoginClicked()
        setupToolbar()

        binding.content.ivProductImage.setOnClickListener { checkPermissions() }

//        dropDownMenu()
    }

    override fun onResume() {
        super.onResume()
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.auth.root.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.content.toolbar.toolbarTitle.text = "Lengkapi Detail Produk"
        binding.content.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.auth.root.isVisible = false
            } else {
                binding.content.root.isVisible = false
                binding.auth.root.isVisible = true
            }
        }
    }

    private fun onLoginClicked() {
        binding.auth.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    private fun dropDownMenu() {
//        val category = arrayOf("Hobi", "Kendaraan", "Aksesoris")
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_profile_city, category)
//        binding.etProductCategory.setAdapter(arrayAdapter)
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
        galleryResult.launch("image/*")
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { cameraResult.launch(it) }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
    }
}