package id.binar.fp.secondhand.ui.main.notification.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemNotificationListBinding

class NotificationFragment : Fragment() {

    private var _binding: ItemNotificationListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = ItemNotificationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toNotifDetaiFrag()
    }

    private fun toNotifDetaiFrag() {
        binding.itemList.setOnClickListener {
            findNavController().navigate(R.id.action_notificationFragment_to_notificationDetailFragment)
        }
    }

}