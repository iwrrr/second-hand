package id.binar.fp.secondhand.ui.main.home.homeProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentHomeBinding
import id.binar.fp.secondhand.databinding.FragmentHomeProductBinding


class HomeProductFragment : Fragment() {
    private var _binding: FragmentHomeProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}