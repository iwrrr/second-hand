package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.ImageBottomSheet
import id.binar.fp.secondhand.ui.main.seller.SellerFragment
import id.binar.fp.secondhand.util.Extensions.clear
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import java.io.File
import kotlin.collections.set

@AndroidEntryPoint
class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private val categoryIds = arrayListOf<Int>()
    private val categoryNames = arrayListOf<String>()

    private var getFile: File? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddProductBinding
        get() = FragmentAddProductBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val isLightStatusBar: Boolean
        get() = true

    override fun setup() {
        super.setup()
        observeCategories()
        onChooseImage()
        onAddProduct()
        onPreviewClicked()
    }

    override fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Tambah Produk"
        binding.toolbar.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun setupCategories(categories: List<Category>) {
        val mapCategory = mutableMapOf<String, Int>()
        val list: ArrayList<String?> = arrayListOf()

        for (i in categories.map { it.name }) {
            list.add(i)
        }

        for (items in categories) {
            mapCategory[items.name] = items.id
        }

        val categoryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_add_product_category,
            list
        )

        binding.content.etProductCategory.apply {
            setAdapter(categoryAdapter)
            setOnItemClickListener { adapter, _, position, _ ->
                val categoryName = adapter.getItemAtPosition(position) as String
                val categoryId = mapCategory.getValue(categoryName)

                categoryAdapter.remove(categoryName)

                categoryIds.add(categoryId)
                categoryNames.add(categoryName)

                setText("Pilih Kategori")
                text.clear()

                setupChips(categoryId, categoryName)
            }
        }
    }

    private fun setupChips(categoryId: Int, categoryName: String?) {
        val chip = Chip(requireContext())
        chip.apply {
            text = categoryName
            isCloseIconVisible = true
            isClickable = true
            binding.content.chipGroup.addView(chip)
            setOnCloseIconClickListener {
                binding.content.chipGroup.removeView(chip)
                categoryIds.remove(categoryId)
                categoryNames.remove(categoryName)
            }
        }
    }

    private fun setupBottomSheet() {
        val bottomSheet = ImageBottomSheet()
        bottomSheet.show(childFragmentManager, ImageBottomSheet.TAG)
        bottomSheet.bottomSheetCallback = object : ImageBottomSheet.BottomSheetCallback {
            override fun onSelectImage(bitmap: Bitmap, file: File) {
                binding.content.ivProductImage.loadImage(bitmap)
                getFile = file
                bottomSheet.dismiss()
            }

            override fun onSelectImage(uri: Uri, file: File) {
                binding.content.ivProductImage.loadImage(uri)
                getFile = file
                bottomSheet.dismiss()
            }
        }
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.layoutButton.isVisible = true
                binding.auth.root.isVisible = false
            } else {
                binding.content.root.isVisible = false
                binding.layoutButton.isVisible = false
                binding.auth.root.isVisible = true
                onLoginClicked()
            }
        }
    }

    private fun onLoginClicked() {
        binding.auth.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    private fun observeCategories() {
        productViewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data != null) {
                        setupCategories(result.data)
                    }
                }
                is Result.Error -> {}
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
                    binding.progressBar.isVisible = true
                }
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    Helper.showToast(requireContext(), "Produk berhasil diterbitkan")
                    parentFragmentManager.beginTransaction().apply {
                        add(R.id.main_nav_host, SellerFragment())
                        addToBackStack(null)
                        commit()
                    }
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Helper.showToast(requireContext(), result.message.toString())
                }
            }
        }
    }

    private fun onChooseImage() {
        binding.content.ivProductImage.setOnClickListener { checkPermissions(::setupBottomSheet) }
    }

    private fun onAddProduct() {
        binding.content.apply {
            binding.btnPublish.setOnClickListener {
                val name = etProductName.text.toString()
                val description = etProductDescription.text.toString()
                val basePrice = etProductPrice.text.toString()
                val location = etProductLocation.text.toString()

                if (categoryIds.isEmpty()) {
                    Helper.showToast(requireContext(), "Pilih minimal 1 kategori")
                    return@setOnClickListener
                }

                if (getFile != null && validateData(name, description, basePrice, location)) {
                    val image = getFile as File
                    observeAddProduct(name, description, basePrice, categoryIds, location, image)
                } else {
                    Helper.showToast(
                        requireContext(),
                        "Silahkan masukkan foto produk terlebih dahulu."
                    )
                }
            }
        }
    }

    private fun onPreviewClicked() {
        binding.btnPreview.setOnClickListener {
            val name = binding.content.etProductName.text.toString()
            val description = binding.content.etProductDescription.text.toString()
            val basePrice = binding.content.etProductPrice.text.toString()
            val location = binding.content.etProductLocation.text.toString()

            if (categoryIds.isEmpty()) {
                Helper.showToast(requireContext(), "Pilih minimal 1 kategori")
                return@setOnClickListener
            }

            val map: Map<Int, String> = categoryIds.zip(categoryNames).toMap()
            val categories = arrayListOf<Category>()

            for (item in map) {
                categories.add(Category(item.key, item.value))
            }

            if (getFile != null && validateData(name, description, basePrice, location)) {
                val image = getFile as File
                val previewProductFragment = PreviewProductFragment()
                val bundle = Bundle().apply {
                    putString("name", name)
                    putString("description", description)
                    putString("base_price", basePrice)
                    putString("image_path", image.absolutePath)
                    putParcelableArrayList("categories", categories)
                }
                previewProductFragment.arguments = bundle
                parentFragmentManager.beginTransaction().apply {
                    add(R.id.main_nav_host, previewProductFragment)
                    hide(this@AddProductFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private fun validateData(
        name: String,
        description: String,
        basePrice: String,
        location: String,
    ): Boolean {
        return when {
            name.isBlank() -> {
                binding.content.etlProductName.error = "Nama produk tidak boleh kosong"
                binding.content.etlProductName.requestFocus()
                false
            }
            description.isBlank() -> {
                binding.content.etlProductDescription.error = "Deskripsi produk tidak boleh kosong"
                binding.content.etlProductDescription.requestFocus()
                false
            }
            basePrice.isBlank() -> {
                binding.content.etlProductPrice.error = "Harga tidak boleh kosong"
                binding.content.etlProductPrice.requestFocus()
                false
            }
            location.isBlank() -> {
                binding.content.etlProductLocation.error = "Lokasi tidak boleh kosong"
                binding.content.etlProductLocation.requestFocus()
                false
            }
            else -> true
        }
    }

    fun clearView() {
        binding.content.apply {
            chipGroup.removeAllViews()
            ivProductImage.clear()
            etProductName.text?.clear()
            etProductPrice.text?.clear()
            etProductLocation.text?.clear()
            etProductDescription.text?.clear()
        }
    }
}