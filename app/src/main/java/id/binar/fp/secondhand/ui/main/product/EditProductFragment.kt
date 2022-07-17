package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentEditProductBinding
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.bottomsheet.ImageBottomSheet
import id.binar.fp.secondhand.util.Extensions.clear
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import java.io.File
import kotlin.collections.set

@AndroidEntryPoint
class EditProductFragment : BaseFragment<FragmentEditProductBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private val categoryIds = arrayListOf<Int>()
    private val categoryNames = arrayListOf<String?>()
    private val newCategories = arrayListOf<Category>()

    private var getFile: File? = null
    private var productId: Int = 0

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEditProductBinding
        get() = FragmentEditProductBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        clearView()
    }

    override fun setup() {
        super.setup()
        initProduct()
        observeCategories()
        onChooseImage()
        onEditProduct()
        onLoginClicked()
    }

    override fun setupToolbar() {
        binding.toolbar.toolbarTitle.text = "Ubah Produk"
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setupCategories(categories: List<Category>) {
        val mapCategory = mutableMapOf<String, Int>()
        val list: ArrayList<String?> = arrayListOf()

        for (i in categories.map { it.name }) {
            if (i !in categoryNames) {
                list.add(i)
            }
        }

        for (items in categories) {
            if (items !in newCategories) {
                mapCategory[items.name] = items.id
            }
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
                observeCategories()
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
                binding.auth.root.isVisible = false
            } else {
                binding.content.root.isVisible = false
                binding.auth.root.isVisible = true
            }
        }
    }

    private fun initProduct() {
        val productId = arguments?.getInt("product_id") as Int
        this.productId = productId
        productViewModel.getSellerProductById(productId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data != null) {
                        val categories = mutableMapOf<Int, String>()

                        result.data.categories?.let { list ->
                            for (category in list) {
                                categories[category.id] = category.name
                            }
                        }

                        for (category in categories) {
                            setupChips(category.key, category.value)
                            categoryIds.add(category.key)
                            categoryNames.add(category.value)
                            newCategories.add(Category(category.key, category.value))
                        }

                        binding.content.apply {
                            etProductName.setText(result.data.name)
                            etProductPrice.setText(result.data.basePrice.toString())
                            etProductLocation.setText(result.data.location)
                            etProductDescription.setText(result.data.description)
                            ivProductImage.loadImage(result.data.imageUrl)
                        }
                    }
                }
                is Result.Error -> {}
            }
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

    private fun observeEditProduct(
        id: Int,
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int?>,
        location: String,
        image: File? = null
    ) {
        productViewModel.updateProduct(
            id,
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
                    Helper.showToast(requireContext(), "Produk berhasil diperbarui")
                    parentFragmentManager.popBackStack()
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

    private fun onEditProduct() {
        binding.content.apply {

            etProductName.doOnTextChanged { text, _, _, count ->
                if (text.isNullOrBlank()) {
                    etlProductName.error = "Nama produk tidak boleh kosong"
                }
                if (count > 0) {
                    etlProductName.isErrorEnabled = false
                }
            }
            etProductDescription.doOnTextChanged { text, _, _, count ->
                if (text.isNullOrBlank()) {
                    etlProductDescription.error = "Deskripsi produk tidak boleh kosong"
                }
                if (count > 0) {
                    etlProductDescription.isErrorEnabled = false
                }
            }
            etProductPrice.doOnTextChanged { text, _, _, count ->
                if (text.isNullOrBlank()) {
                    etlProductPrice.error = "Harga tidak boleh kosong"
                }
                if (count > 0) {
                    etlProductPrice.isErrorEnabled = false
                }
            }
            etProductLocation.doOnTextChanged { text, _, _, count ->
                if (text.isNullOrBlank()) {
                    etlProductLocation.error = "Lokasi tidak boleh kosong"
                }
                if (count > 0) {
                    etlProductLocation.isErrorEnabled = false
                }
            }

            binding.btnPublish.setOnClickListener {
                val name = etProductName.text.toString()
                val description = etProductDescription.text.toString()
                val basePrice = etProductPrice.text.toString()
                val location = etProductLocation.text.toString()

                if (categoryIds.isEmpty()) {
                    Helper.showToast(requireContext(), "Pilih minimal 1 kategori")
                    return@setOnClickListener
                }

                if (validateData(name, description, basePrice, location)) {
                    if (getFile != null) {
                        val image = getFile as File
                        observeEditProduct(
                            productId,
                            name,
                            description,
                            basePrice,
                            categoryIds,
                            location,
                            image
                        )
                    } else {
                        observeEditProduct(
                            productId,
                            name,
                            description,
                            basePrice,
                            categoryIds,
                            location
                        )
                    }
                }
            }
        }
    }

    private fun onLoginClicked() {
        binding.auth.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
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

    private fun clearView() {
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