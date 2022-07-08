package id.binar.fp.secondhand.ui.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.databinding.FragmentProductDetailBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.main.bottomsheet.BidBottomSheet
import id.binar.fp.secondhand.ui.main.bottomsheet.BottomSheetCallback
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Helper
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.getWindowInsetsController(requireActivity().window.decorView)?.isAppearanceLightStatusBars =
            false
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false

        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }

        observeDetailProduct()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeDetailProduct() {
        val id = arguments?.getInt("id")
        id?.let {
            viewModel.getDetailProduct(it).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.loading.root.isVisible = true
                    }
                    is Result.Success -> {
                        binding.loading.root.isVisible = false
                        binding.tvProductName.text = result.data.name
                        binding.tvProductCategory.text = Helper.initCategory(result.data.categories)
                        binding.tvProductPrice.text = result.data.basePrice.toString()
                        binding.tvName.text = result.data.user?.fullName
                        binding.tvCity.text = result.data.user?.city
                        binding.tvProductDescription.text = result.data.description
                        binding.ivProductImage.loadImage(result.data.imageUrl)
                        binding.ivProfile.loadImage(result.data.user?.imageUrl)
                        binding.btnBid.isEnabled = result.data.status == "available"

                        initBottomSheet(result.data)
                    }
                    is Result.Error -> {
                        binding.loading.root.isVisible = false
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initBottomSheet(product: ProductDto) {
        val bidBottomSheet = BidBottomSheet()
        val bundle = Bundle().apply {
            putInt("id", product.id)
            putString("image", product.imageUrl)
            putString("name", product.name)
            putInt("price", product.basePrice)
        }

        bidBottomSheet.arguments = bundle

        binding.btnBid.setOnClickListener {
            authViewModel.getToken().observe(viewLifecycleOwner) { token ->
                if (!token.isNullOrBlank()) {
                    bidBottomSheet.show(parentFragmentManager, BidBottomSheet.TAG)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Silakan login terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                }
            }
        }

        bidBottomSheet.bottomSheetCallback = object : BottomSheetCallback {
            override fun onDismiss() {
                if (bidBottomSheet.statusOrder == 1) {
                    binding.btnBid.isEnabled = false
                    binding.btnBid.text = "Menunggu respon penjual"
                }
            }
        }
    }
}