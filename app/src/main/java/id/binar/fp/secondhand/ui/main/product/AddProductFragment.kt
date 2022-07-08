package id.binar.fp.secondhand.ui.main.product

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
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.BottomSheetChooseImageBinding
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.createTempFile
import id.binar.fp.secondhand.util.uriToFile
import java.io.File
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableMap
import kotlin.collections.arrayListOf
import kotlin.collections.getValue
import kotlin.collections.map
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toList

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private var getFile: File? = null
    private val idCategory: ArrayList<Int?> = arrayListOf()
    private val mapCategory: MutableMap<String?, Int?> = mutableMapOf()
    private lateinit var currentPhotoPath: String


    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val file = File(currentPhotoPath)
                val bitmap = BitmapFactory.decodeFile(file.path)

                getFile = file

                Glide.with(this)
                    .load(bitmap)
                    .into(binding.content.ivProductImage)

            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage: Uri = result.data?.data as Uri
                val file = uriToFile(selectedImage, requireContext())


                getFile = file

                Glide.with(this)
                    .load(file)
                    .into(binding.content.ivProductImage)

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
        productAdd()

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
                        val listCategory: ArrayList<String?> = arrayListOf()
                        for (i in response.map { it.name }) {
                            listCategory.add(i)
                        }

                        for (items in response) {
                            mapCategory[items.name] = items.id
                        }
                        val listAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.item_add_product_category,
                            listCategory
                        )

                        binding.content.etProductCategory.setAdapter(listAdapter)
                        binding.content.etProductCategory.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
                        binding.content.etProductCategory.setOnItemClickListener { adapterView, view, i, l ->
                            val selectedCategory = adapterView.getItemAtPosition(i).toString()
                            idCategory.add(mapCategory.getValue(selectedCategory))
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
        basePrice: String,
        categoryIds: List<Int?>,
        location: String,
        image: File
    ) {
        productViewModel.addProduct(
            name,
            description,
            basePrice,
            categoryIds,
            location,
            image
        ).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Produk sedang di terbitkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Produk Berhasil di terbitkan",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().finish()
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun productAdd() {
        binding.apply {
            content.btnPublish.setOnClickListener {
                val name = content.etProductName.text.toString()
                val description = content.etProductDescription.text.toString()
                val basePrice = content.etProductPrice.text.toString()
                val categoryIds: List<Int?> = idCategory.toList()
                val location = content.etProductLocation.toString()
//                Toast.makeText(requireContext(), categoryIds.toString(), Toast.LENGTH_SHORT).show()
                if (getFile != null) {
                    val image = getFile as File
                    observeAddProduct(name, description, basePrice, categoryIds, location, image)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Silahkan masukkan foto produk terlebih dahulu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

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

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private const val FILENAME_FORMAT = "dd-MMM-yyyy"
    }
}