package id.binar.fp.secondhand.ui.main.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetChooseImageBinding
import id.binar.fp.secondhand.databinding.FragmentProfileEditBinding
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.createTempFile
import id.binar.fp.secondhand.util.uriToFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileEditFragment : Fragment() {

    private lateinit var currentPhotoPath: String

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private var getFile: File? = null

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val file = File(currentPhotoPath)
                val bitmap = BitmapFactory.decodeFile(file.path)

                getFile = file

                Glide.with(this)
                    .load(bitmap)
                    .into(binding.ivProfile)

//                binding.ivProfile.setImageBitmap(bitmap)
            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage: Uri = result.data?.data as Uri
                val file = uriToFile(selectedImage, requireContext())

//                val file: File = File(Environment.getExternalStorageDirectory(), "read.me")
//                var auxFile: File = File(selectedImage.getPath())

                getFile = file

                Glide.with(this)
                    .load(file)
                    .into(binding.ivProfile)

//                binding.ivProfile.loadImage(selectedImage)
            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                binding.ivProfile.loadImage(result)
            }
        }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
//                val bitmap = result.data?.extras?.get("data") as Bitmap
                //BITMAP TO STRING -> get path
                val file = File(currentPhotoPath)
                val bitmap = BitmapFactory.decodeFile(file.path)

                getFile = file

                val StringBitmap = bitmap
                //STRING TO FILE
//                val file = File(StringBitmap)

                Glide.with(this)
                    .load(file)
                    .into(binding.ivProfile)
//                binding.ivProfile.loadImage(bitmap)
            }
        }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }


//    private val galleryResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
//            if (result != null) {
//                binding.ivProfile.loadImage(result)
//            }
//        }

//    private val cameraResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK ) {
////                val bitmap = result.data?.extras?.get("data") as Bitmap
//                val file = File(currentPhotoPath)
//                val bitmap = BitmapFactory.decodeFile(file.path)
//
//                getFile = file
//
//                binding.ivProfile.loadImage(bitmap)
//
//                Glide.with(requireContext())
//                    .load(bitmap)
//                    .into(binding.ivProfile)
//            }
//        }

//    private val image = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
//        if (result.resultCode == Activity.RESULT_OK){
//            val data = result.data
//            if (data != null){
//                val selectedImageUri = data.data
//                if (selectedImageUri != null){
//                    try {
//                        val inputStream = requireActivity().contentResolver.openInputStream(selectedImageUri)
//                        val bitmap = BitmapFactory.decodeStream(inputStream)
//                        binding.ivProfile.setImageBitmap(bitmap)
//                    }catch (e: Exception){
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }
//    }

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

//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.resolveActivity(requireActivity().packageManager)
//
//        createTempFile(requireContext()).also {
//            val photoURI: Uri = FileProvider.getUriForFile(
//                requireContext(),
//                "id.binar.fp.secondhand",
//                it
//            )
//            currentPhotoPath = it.absolutePath
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//            launcherCamera.launch(intent)
//        }
//    }


//    private fun openGallery() {
//        galleryResult.launch("image/*")
//    }

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

//    private fun openCamera(){
//        binding.ivProfile.setOnClickListener{ takePhoto()}
//    }
//
//    private fun openGallery(){
//        startGallery()
//    }
//    private fun startGallery() {
//        val intent = Intent()
//        intent.action = Intent.ACTION_GET_CONTENT
//        intent.type = "image/*"
//
//        val chooser = Intent.createChooser(intent, "Choose a Picture")
//        launcherGallery.launch(chooser)
//    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private const val REQUEST_CODE_IMAGE_PICKER = 200
    }

    private fun editProfile() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val phoneNumber = binding.etPhone.text.toString()
            val city = binding.etCity.text.toString()
            val address = binding.etAddress.text.toString()
            val formatter = SimpleDateFormat("yyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)
//            val image = binding.ivProfile.setImageURI(cameraResult)

            if (getFile != null) {
                val file = getFile as File
                upload(name, phoneNumber, city, address, file)
//                Toast.makeText(requireContext(), "Profile berhasil di update", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Silahkan Masukkan foto Profil terlebih dahulu.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun upload(
        fullName: String,
        phoneNumber: String,
        city: String,
        address: String,
        image: File
    ) {
        viewModel.editProfile(fullName, phoneNumber, city, address, image)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.isVisible = true
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            "Profile Berhasil di Update",
                            Toast.LENGTH_SHORT
                        ).show()
                        val fragmentManager = parentFragmentManager
                        fragmentManager.popBackStack()
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }

//    private fun observeEditProfile(
//        name: String,
//        email: String?,
//        password: String?,
//        phoneNumber: String,
//        city: String,
//        address: String
//    ) {
//        val file = getFile as File
//        viewModel.editProfile(name, email, password, phoneNumber, city, address, file )
//            .observe(viewLifecycleOwner) { result ->
//                when (result) {
//                    is Result.Loading -> {
//                        //binding.loading.root.isVisible = true
//                        binding.btnSave.isVisible = false
//                    }
//                    is Result.Success -> {
//                        //binding.loading.root.isVisible = false
//                        binding.btnSave.isVisible = true
//                        requireActivity().finish()
//                    }
//                    is Result.Error -> {
//                        //binding.loading.root.isVisible = false
//                        binding.btnSave.isVisible = true
//                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//    }
//
//    private fun validateData(
//        name: String,
//        phoneNumber: String,
//        city: String,
//        address: String
//    ): Boolean {
//        return when {
//            name.isEmpty() -> {
//                binding.etlName.error = "Nama tidak boleh kosong"
//                binding.etlName.requestFocus()
//                false
//            }
//            phoneNumber.isBlank() -> {
//                binding.etlPhone.error = "Nomor telepon tidak boleh kosong"
//                binding.etlPhone.requestFocus()
//                false
//            }
//            city.isEmpty() -> {
//                binding.etlCity.error = "Kota tidak boleh kosong"
//                binding.etlCity.requestFocus()
//                false
//            }
//            address.isEmpty() -> {
//                binding.etlAddress.error = "Alamat tidak boleh kosong"
//                binding.etlAddress.requestFocus()
//                false
//            }
//            else -> true
//        }
//    }
}