package id.binar.fp.secondhand.ui.main.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.databinding.FragmentProductDetailBinding
import id.binar.fp.secondhand.ui.main.home.HomeViewModel
import id.binar.fp.secondhand.util.Extensions.loadImage
import id.binar.fp.secondhand.util.Result

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            false

        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }

        observeDetailProduct()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeDetailProduct() {
        val value = this.arguments
        val inputData = value?.getInt("id")

        viewModel.getDetailProduct(inputData!!).observe(viewLifecycleOwner){ result->
            when(result){
                is Result.Loading ->{

                }
                is Result.Success -> {
                    binding.tvProductName.text = result.data.name
                    binding.tvProductCategory.text = initCategory(result.data.categories)
                    binding.tvProductPrice.text = result.data.basePrice.toString()
                    binding.tvName.text = result.data.user?.fullName
                    binding.tvCity.text = result.data.user?.city
                    binding.tvProductDescription.text = result.data.description
                    binding.ivProductImage.loadImage(result.data.imageUrl)
                    binding.ivProfile.loadImage(result.data.user?.imageUrl)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initCategory(categories:List<CategoryDto>?):String{
        val list = ArrayList<CategoryDto>()
        categories?.forEach { category->list.add(category) }
        return list.joinToString { it.name!! }
    }
}