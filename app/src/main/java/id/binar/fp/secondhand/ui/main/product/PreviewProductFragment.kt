package id.binar.fp.secondhand.ui.main.product

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentPreviewProductBinding
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result
import java.io.File

@AndroidEntryPoint
class PreviewProductFragment : BaseFragment<FragmentPreviewProductBinding>() {

    private val productViewModel: ProductViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPreviewProductBinding
        get() = FragmentPreviewProductBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val isLightStatusBar: Boolean
        get() = false

    override fun setup() {
        super.setup()
        onBackClicked()
        observeUser()
        initProduct()
    }

    private fun initProduct() {
        val name = arguments?.getString("name") as String
        val description = arguments?.getString("description") as String
        val basePrice = arguments?.getString("base_price") as String
        val imagePath = arguments?.getString("image_path") as String
        val categories = arguments?.getParcelableArrayList<Category>("categories") as List<Category>

        val uri = Uri.fromFile(File(imagePath))

        binding.ivProductImage.loadImage(uri)
        binding.tvProductName.text = name
        binding.tvProductCategory.text = Helper.initCategory(categories)
        binding.tvProductPrice.text = requireContext().getString(
            R.string.text_seller_order_base_price,
            basePrice
        )
        binding.tvProductDescription.text = description

        binding.btnPublish.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeUser() {
        authViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data != null) {
                        binding.tvName.text = result.data.fullName
                        binding.tvCity.text = result.data.city
                        binding.ivProfile.loadImage(result.data.imageUrl)
                    }
                }
                is Result.Error -> {}
            }
        }
    }

    private fun onBackClicked() {
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }
}