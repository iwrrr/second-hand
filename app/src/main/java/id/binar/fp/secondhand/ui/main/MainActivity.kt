package id.binar.fp.secondhand.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ActivityMainBinding
import id.binar.fp.secondhand.ui.main.history.HistoryFragment
import id.binar.fp.secondhand.ui.main.home.HomeFragment
import id.binar.fp.secondhand.ui.main.notification.NotificationFragment
import id.binar.fp.secondhand.ui.main.product.AddProductFragment
import id.binar.fp.secondhand.ui.main.profile.ProfileFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val notificationFragment = NotificationFragment()
    private val addProductFragment = AddProductFragment()
    private val historyFragment = HistoryFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setLightStatusBar(window.decorView, true)
        setupBottomNavigationBar()
    }

    override fun onBackPressed() {
        setLightStatusBar(window.decorView, true)
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            setVisibilityBottomNav(false)
        } else if (supportFragmentManager.backStackEntryCount > 0 && !homeFragment.isHidden) {
            supportFragmentManager.popBackStack()
            setVisibilityBottomNav(true)
        } else if (supportFragmentManager.backStackEntryCount > 0 && (addProductFragment.isHidden && profileFragment.isHidden)) {
            supportFragmentManager.popBackStack()
        } else if (supportFragmentManager.backStackEntryCount > 0 && homeFragment.isHidden) {
            supportFragmentManager.popBackStack()
            addProductFragment.clearView()
            setVisibilityBottomNav(true)
            setTabStateFragment(TabState.PROFILE).commit()
            binding.bottomNavigationView.menu.findItem(R.id.navigation_profile).isChecked = true
        } else if (supportFragmentManager.backStackEntryCount > 0 || !homeFragment.isHidden) {
            super.onBackPressed()
            setVisibilityBottomNav(true)
        } else {
            addProductFragment.clearView()
            setVisibilityBottomNav(true)
            setTabStateFragment(TabState.HOME).commit()
            binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
        }
    }

    private fun setupBottomNavigationBar() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_nav_host, homeFragment)
            .add(R.id.main_nav_host, notificationFragment)
            .add(R.id.main_nav_host, addProductFragment)
            .add(R.id.main_nav_host, historyFragment)
            .add(R.id.main_nav_host, profileFragment)
            .commit()

        setTabStateFragment(TabState.HOME).commit()

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    setTabStateFragment(TabState.HOME).commit()
                }
                R.id.navigation_notification -> {
                    setTabStateFragment(TabState.NOTIFICATION).commit()
                }
                R.id.navigation_history -> {
                    setTabStateFragment(TabState.HISTORY).commit()
                }
                R.id.navigation_profile -> {
                    setTabStateFragment(TabState.PROFILE).commit()
                }
            }
            true
        }
        binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true

        binding.fab.setOnClickListener {
            setTabStateFragment(TabState.ADDPRODUCT).commit()
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
                transaction.hide(historyFragment)
                transaction.hide(profileFragment)
            }
            TabState.NOTIFICATION -> {
                transaction.hide(homeFragment)
                transaction.show(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(historyFragment)
                transaction.hide(profileFragment)
            }
            TabState.ADDPRODUCT -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.show(addProductFragment)
                transaction.hide(historyFragment)
                transaction.hide(profileFragment)
                setVisibilityBottomNav(false)
            }
            TabState.HISTORY -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.show(historyFragment)
                transaction.hide(profileFragment)
            }
            TabState.PROFILE -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(historyFragment)
                transaction.show(profileFragment)
            }
        }
        return transaction
    }

    private fun setLightStatusBar(view: View, isLight: Boolean) {
        ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = isLight
    }

    private fun setVisibilityBottomNav(isVisible: Boolean) {
        binding.bottomAppBar.isVisible = isVisible
        if (isVisible) binding.fab.show() else binding.fab.hide()
    }

    private enum class TabState {
        HOME,
        NOTIFICATION,
        ADDPRODUCT,
        HISTORY,
        PROFILE,
    }
}