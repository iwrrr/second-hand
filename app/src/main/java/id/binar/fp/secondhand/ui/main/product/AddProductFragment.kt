package id.binar.fp.secondhand.ui.main.product

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.databinding.BottomSheetChooseImageBinding
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result
import okhttp3.MultipartBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private var getFile: File? = null
    private var dataId: Int? = 0
    private var dataLoc: String? = ""
    private lateinit var dataCategory: List<Int>

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result != null) {
//                binding.content.ivProductImage.loadImage(result)
//            }

            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage: Uri = result.data?.data as Uri
                val file = uriToFile(selectedImage, requireContext())

                getFile = file

                Glide.with(this)
                    .load(file)
                    .into(binding.content.ivProductImage)
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
        categoryList()
        addProduct()
        observeUser()

        onLoginClicked()
        setupToolbar()

        binding.content.ivProductImage.setOnClickListener { checkPermissions() }

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

    private fun categoryList() {
        productViewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    result.data.let { response ->
                        val listAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.item_add_product_category,
                            response.map { it.name })
                        binding.content.etProductCategory.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
                        binding.content.etProductCategory.setAdapter(listAdapter)
                        binding.content.etProductCategory.onItemSelectedListener = object :

                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                val selectedName = p0.getItemAtPosition(p2).toString()
                                Toast.makeText(requireContext(), selectedName, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }
                    }
                }
                is Result.Error -> {

                }
            }
        }

    }

    private fun observeAddProduct(
        name: String,
        description: String,
        basePrice: Int,
        categoryIds: List<Int>,
        location: String,
        userId: Int,
        image: File
    ) {
        productViewModel.addProduct(
            name,
            description,
            basePrice,
            categoryIds,
            location,
            userId,
            image
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    requireActivity().finish()
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun observeCategory() {
        productViewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    result.data.let { response ->
                        response.map { it.name }
                    }
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

                }
            }
        }
    }


    private fun observeUser() {

        productViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    result.data.let { response ->
                        dataId = response.id
                        dataLoc = response.city
                    }
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

                }
            }
        }
    }

    private fun addProduct() {
        productViewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    result.data.let { response ->
//                        binding.apply {
//                            content.btnPublish.setOnClickListener {
//                                val name = content.etProductName.text.toString()
//                                val description = content.etProductDescription.text.toString()
//                                val basePrice = content.etProductPrice.text.toString().toInt()
//                                content.etProductCategory.onItemSelectedListener = object :
//
//                                    AdapterView.OnItemSelectedListener {
//                                    override fun onItemSelected(
//                                        p0: AdapterView<*>?,
//                                        p1: View?,
//                                        p2: Int,
//                                        p3: Long
//                                    ) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onNothingSelected(p0: AdapterView<*>?) {
//                                        TODO("Not yet implemented")
//                                    }
//                                }
//                                val location = dataLoc
//                                val userId = dataId
//                                val image = getFile as File
//
//                                observeAddProduct(
//                                    name,
//                                    description,
//                                    basePrice,
//                                    categoryIds,
//                                    location!!,
//                                    userId!!,
//                                    image
//                                )
//
//                            }
//
//                        }
                    }
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

                }

            }
        }
    }


    private fun productAdd() {
        binding.apply {
            content.btnPublish.setOnClickListener {
                val name = content.etProductName.text.toString()
                val description = content.etProductDescription.text.toString()
                val basePrice = content.etProductPrice.text.toString().toInt()
                val categoryIds = content.etProductCategory
                val location = dataLoc
                val userId = dataId
//                val image: MultipartBody.Part =


//                observeAddProduct(name, description, basePrice, categoryIds, location, userId)

            }

        }
    }

    private fun validateData(
        name: String,
        description: String,
        categoryIds: List<CategoryDto>,
        image: MultipartBody.Part,
    ): Boolean {
        return when {
            name.isEmpty() -> {
                binding.content.etlProductName.error = "Nama produk tidak boleh kosong"
                binding.content.etProductName.requestFocus()
                false
            }
            description.isEmpty() -> {
                binding.content.etlProductDescription.error = "Deskripsi tidak boleh kosong"
                binding.content.etProductDescription.requestFocus()
                false
            }
            categoryIds.isEmpty() -> {
                binding.content.etlProductCategory.error = "Kategori tidak boleh kosong"
                binding.content.etProductCategory.requestFocus()
                false
            }
            else -> true
        }
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
        galleryResult.launch(chooser)
//        galleryResult.launch("image/*")
    }

    private fun uriToFile(selectedImage: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val file = createTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
        val outputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return file
    }

    private fun createTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())


    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            cameraResult.launch(it)
        }
//        intent.resolveActivity(packageManager)
//
//        createTempFile(application).also {
//            val photoURI: Uri = FileProvider.getUriForFile(
//                this@AddProductFragment,
//                "com.dicoding.bpaai.submission",
//                it
//            )
//            currentPhotoPath = it.absolutePath
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//            launcherCamera.launch(intent)
//        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private const val FILENAME_FORMAT = "dd-MMM-yyyy"
    }
}