package id.binar.fp.secondhand.ui.main.adapter.sell

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.binar.fp.secondhand.ui.main.sell.interested.SellListInterestedFragment
import id.binar.fp.secondhand.ui.main.sell.product.SellListProductFragment
import id.binar.fp.secondhand.ui.main.sell.sold.SellListSoldFragment

class SellListPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = SellListProductFragment()
            1 -> fragment = SellListInterestedFragment()
            2 -> fragment = SellListSoldFragment()
        }
        return fragment as Fragment
    }
}