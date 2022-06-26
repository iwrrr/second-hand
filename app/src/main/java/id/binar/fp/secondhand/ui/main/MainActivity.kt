package id.binar.fp.secondhand.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ActivityMainBinding
import id.binar.fp.secondhand.ui.main.home.HomeFragment
import id.binar.fp.secondhand.ui.main.notification.NotificationFragment
import id.binar.fp.secondhand.ui.main.product.AddProductFragment
import id.binar.fp.secondhand.ui.main.profile.ProfileFragment
import id.binar.fp.secondhand.ui.main.sell.SellListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val notificationFragment = NotificationFragment()
    private val addProductFragment = AddProductFragment()
    private val sellListFragment = SellListFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_nav_host, homeFragment)
            .add(R.id.main_nav_host, notificationFragment)
            .add(R.id.main_nav_host, addProductFragment)
            .add(R.id.main_nav_host, sellListFragment)
            .add(R.id.main_nav_host, profileFragment)
            .commit()

        setTabStateFragment(TabState.HOME).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    setTabStateFragment(TabState.HOME).commit()
                    binding.bottomNavigationView.isVisible = true
                }
                R.id.navigation_notification -> {
                    setTabStateFragment(TabState.NOTIFICATION).commit()
                    binding.bottomNavigationView.isVisible = true
                }
                R.id.navigation_add_product -> {
                    setTabStateFragment(TabState.ADDPRODUCT).commit()
                    binding.bottomNavigationView.isVisible = false
                }
                R.id.navigation_sell_list -> {
                    setTabStateFragment(TabState.SELLLIST).commit()
                    binding.bottomNavigationView.isVisible = true
                }
                R.id.navigation_profile -> {
                    setTabStateFragment(TabState.PROFILE).commit()
                    binding.bottomNavigationView.isVisible = true
                }
            }
            true
        }
        binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0 || !homeFragment.isHidden) {
            super.onBackPressed()
            binding.bottomNavigationView.isVisible = true
        } else {
            setTabStateFragment(TabState.HOME).commit()
            binding.bottomNavigationView.isVisible = true
            binding.bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true
        }
    }

    private fun setTabStateFragment(state: TabState): FragmentTransaction {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
        when (state) {
            TabState.HOME -> {
                transaction.show(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellListFragment)
                transaction.hide(profileFragment)
            }
            TabState.NOTIFICATION -> {
                transaction.hide(homeFragment)
                transaction.show(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellListFragment)
                transaction.hide(profileFragment)
            }
            TabState.ADDPRODUCT -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.show(addProductFragment)
                transaction.hide(sellListFragment)
                transaction.hide(profileFragment)
            }
            TabState.SELLLIST -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.show(sellListFragment)
                transaction.hide(profileFragment)
            }
            TabState.PROFILE -> {
                transaction.hide(homeFragment)
                transaction.hide(notificationFragment)
                transaction.hide(addProductFragment)
                transaction.hide(sellListFragment)
                transaction.show(profileFragment)
            }
        }
        return transaction
    }

    internal enum class TabState {
        HOME,
        NOTIFICATION,
        ADDPRODUCT,
        SELLLIST,
        PROFILE,
    }
}