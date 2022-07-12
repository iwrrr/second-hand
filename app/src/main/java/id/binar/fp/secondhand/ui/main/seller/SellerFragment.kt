package id.binar.fp.secondhand.ui.main.seller

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.FragmentSellerBinding
import id.binar.fp.secondhand.ui.auth.AuthActivity
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.base.BaseFragment
import id.binar.fp.secondhand.ui.main.adapter.seller.SellerPagerAdapter

@AndroidEntryPoint
class SellerFragment : BaseFragment<FragmentSellerBinding>() {

    private val authViewModel: AuthViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSellerBinding
        get() = FragmentSellerBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        setupViewPager()

        onBackClicked()
        onLoginClicked()
    }

    override fun checkAuth() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrBlank()) {
                binding.content.root.isVisible = true
                binding.auth.root.isVisible = false
//                observeUser()
            } else {
                binding.content.root.isVisible = false
                binding.auth.root.isVisible = true
            }
        }
    }

    private fun onBackClicked() {
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun onLoginClicked() {
        binding.auth.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = SellerPagerAdapter(childFragmentManager, lifecycle)
        val viewpager = binding.content.viewPager
        viewpager.apply {
            adapter = pagerAdapter
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                toggleRefreshing(state == ViewPager2.SCROLL_STATE_IDLE)
            }
        })

        val tabs = binding.content.tabs
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun toggleRefreshing(enabled: Boolean) {
        binding.swipeRefresh.isEnabled = enabled
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_sell_list_1,
            R.string.tab_sell_list_2,
            R.string.tab_sell_list_3
        )
    }
}