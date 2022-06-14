package id.binar.fp.secondhand.ui.main.sell.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.databinding.FragmentSellListProductBinding
import id.binar.fp.secondhand.ui.main.adapter.sell.SellListProductAdapter
import id.binar.fp.secondhand.util.dummy.Product

@AndroidEntryPoint
class SellListProductFragment : Fragment() {

    private var _binding: FragmentSellListProductBinding? = null
    private val binding get() = _binding!!

    private val productAdapter by lazy {
        SellListProductAdapter {
            Toast.makeText(
                requireContext(),
                it.name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellListProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        productAdapter.submitList(setDummyData())
    }

    private fun setDummyData(): ArrayList<Product> {
        val product1 = Product(
            1,
            "Jam Tangan Casio",
            "Rp210.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product2 = Product(
            2,
            "Jam Tangan Casio",
            "Rp220.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product3 = Product(
            3,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product4 = Product(
            4,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product5 = Product(
            5,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product6 = Product(
            6,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product7 = Product(
            7,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product8 = Product(
            8,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )

        val products = ArrayList<Product>()

        products.add(product1)
        products.add(product2)
        products.add(product3)
        products.add(product4)
        products.add(product5)
        products.add(product6)
        products.add(product7)
        products.add(product8)

        return products
    }
}