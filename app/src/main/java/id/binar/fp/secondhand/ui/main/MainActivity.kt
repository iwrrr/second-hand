package id.binar.fp.secondhand.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ActivityMainBinding
import id.binar.fp.secondhand.ui.auth.AuthViewModel
import id.binar.fp.secondhand.ui.main.home.HomeFragment
import id.binar.fp.secondhand.ui.main.notification.NotificationFragment
import id.binar.fp.secondhand.ui.main.product.AddProductFragment
import id.binar.fp.secondhand.ui.main.profile.ProfileFragment
import id.binar.fp.secondhand.ui.main.seller.SellerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()

    private val homeFragment = HomeFragment()
    private val notificationFragment = NotificationFragment()
    private val addProductFragment = AddProductFragment()
    private val sellerFragment = SellerFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setLightStatusBar(window.decorView, true)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_nav_host, homeFragment)
            .add(R.id.main_nav_host, notificationFragment)
            .add(R.id.main_nav_host, addProductFragment)
            .add(R.id.main_nav_host, sellerFragment)
            .add(R.id.main_nav_host, profileFragment)
            .commit()

        setTabStateFragment(TabState.HOME).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    setTabStateFragment(TabState.HOME).commit()
                }
                R.id.navigation_notification -> {
                    setTabStateFragment(TabState.NOTIFICATION).commit()
                }
                R.id.navigation_add_product -> {
                    setTabStateFragment(TabState.ADDPRODUCT).commit()
                }
                R.id.navigation_sell_list -> {
                    setTabStateFragment(TabState.SELLLIST).commit()
                }
                R.id.navigation_profile -> {
                    setTabStateFragment(TabState.PROFILE).commit()
                }
            }
            true
        }
        binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
    }

    override fun onBackPressed() {
        setLightStatusBar(window.decorView, true)
        val tag = supportFragmentManager.findFragmentByTag(profileFragment::class.java.simpleName)
        if (tag != null) {
            profileFragment.observeUser()
        }
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            setVisibilityBottomNav(false)
        } else if (supportFragmentManager.backStackEntryCount > 0 || !homeFragment.isHidden) {
            super.onBackPressed()
            setVisibilityBottomNav(true)
        } else {
            setTabStateFragment(TabState.HOME).commit()
            setVisibilityBottomNav(true)
            binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
        }
    }

    private fun setTabStateFragment(state: TabState): FragmentTransaction {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
        setVisibilityBottomNav(true)
        when (state) {
            TabState.HOME -> {
                transaction.show(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellerFragment)
                transaction.hide(profileFragment)
            }
            TabState.NOTIFICATION -> {
                transaction.hide(homeFragment)
                transaction.show(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellerFragment)
                transaction.hide(profileFragment)
            }
            TabState.ADDPRODUCT -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.show(addProductFragment)
                transaction.hide(sellerFragment)
                transaction.hide(profileFragment)
                authViewModel.getToken().observe(this) { token ->
                    setVisibilityBottomNav(token.isNullOrBlank())
                }
            }
            TabState.SELLLIST -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.show(sellerFragment)
                transaction.hide(profileFragment)
            }
            TabState.PROFILE -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellerFragment)
                transaction.show(profileFragment)
            }
        }
        return transaction
    }

    private fun setLightStatusBar(view: View, isLight: Boolean) {
        ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = isLight
    }

    private fun setVisibilityBottomNav(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
    }

    private enum class TabState {
        HOME,
        NOTIFICATION,
        ADDPRODUCT,
        SELLLIST,
        PROFILE,
    }
}