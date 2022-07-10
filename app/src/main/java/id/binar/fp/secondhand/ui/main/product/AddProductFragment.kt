package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentAddProductBinding
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
class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {

    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    private val idCategory: ArrayList<Int?> = arrayListOf()
    private val mapCategory: MutableMap<String?, Int?> = mutableMapOf()

    private var getFile: File? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddProductBinding
        get() = FragmentAddProductBinding::inflate

    override fun onStop() {
        super.onStop()
        binding.content.ivProductImage.clear()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.content.ivProductImage.clear()
    }

    override fun setup() {
        super.setup()
        initListCategory()
        chooseImage()
        addProduct()
    }

    override fun setupToolbar() {
        binding.content.toolbar.toolbarTitle.text = "Lengkapi Detail Produk"
        binding.content.toolbar.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.auth.root.isVisible = false
            } else {
                binding.content.root.isVisible = false
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

    private fun initListCategory() {
        productViewModel.getCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
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

                        binding.content.etProductCategory.apply {
                            setAdapter(listAdapter)
                            setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
                            setOnItemClickListener { adapterView, _, i, _ ->
                                val selectedCategory = adapterView.getItemAtPosition(i).toString()
                                idCategory.add(mapCategory.getValue(selectedCategory))
                            }
                        }
                    }
                }
                is Result.Error -> {}
            }
        }
    }

    private fun chooseImage() {
        binding.content.ivProductImage.setOnClickListener { checkPermissions(::initBottomSheet) }
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
                    parentFragmentManager.popBackStack()
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Helper.showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun addProduct() {
        binding.content.apply {
            btnPublish.setOnClickListener {
                val name = etProductName.text.toString()
                val description = etProductDescription.text.toString()
                val basePrice = etProductPrice.text.toString()
                val categoryIds: List<Int?> = idCategory.toList()
                val location = etProductLocation.toString()

                if (getFile != null) {
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

    private fun initBottomSheet() {
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
}