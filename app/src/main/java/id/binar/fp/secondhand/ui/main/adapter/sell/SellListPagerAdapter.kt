package id.binar.fp.secondhand.ui.main.adapter.sell

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.binar.fp.secondhand.ui.main.sell.interested.InterestedFragment
import id.binar.fp.secondhand.ui.main.sell.product.ProductFragment
import id.binar.fp.secondhand.ui.main.sell.sold.SoldFragment

class SellListPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ProductFragment()
            1 -> fragment = InterestedFragment()
            2 -> fragment = SoldFragment()
        }
        return fragment as Fragment
    }
}